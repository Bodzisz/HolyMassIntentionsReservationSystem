import React, { useEffect, useState } from "react";
import { config } from "../config/config";
import {
  Container,
  Title,
  createStyles,
  rem,
  Group,
  SimpleGrid,
  Table,
  Button,
  Modal,
  Textarea,
  Loader,
  Center,
  Alert,
  Indicator,
  Text,
} from "@mantine/core";
import { DatePicker } from "@mantine/dates";
import dayjs from "dayjs";
import { formatDate } from "../util/dateFormatter";
import { getUser } from "../context/user";
import { getHeaders } from "../util/requestHeaderProvider";

const useStyles = createStyles((theme) => ({
  wrapper: {
    paddingBottom: rem(190),
  },
  title: {
    paddingBottom: rem(50),
  },
}));

function ChurchIntentionsReservation({ church, goBackToChurchList }) {
  const { classes } = useStyles();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [holyMasses, setHolyMasses] = useState(null);
  const [holyMassesLoading, setHolyMassesLoading] = useState(false);
  const [holyMassesError, setHolyMassesError] = useState(false);
  const [currentMasses, setCurrentMasses] = useState(null);
  const [futureMasses, setFutureMasses] = useState(null);
  const [selectedMass, setSelectedMass] = useState(null);
  const [isIntentionSaved, setIsIntentionSaved] = useState(false);
  const [isIntentionSavingError, setIsIntentionSavingError] = useState(false);
  const [intentionContent, setIntentionContent] = useState("");
  const [intentions, setIntentions] = useState(null);
  const [currentIntentions, setCurrentIntentions] = useState(null);
  const [intentionsLoading, setIntentionsLoading] = useState(false);
  const [intentionsError, setIntentionsError] = useState(false);

  const DEFAULT_HEADERS = {
    method: "GET",
    headers: getHeaders(),
  };

  useEffect(() => {
    setHolyMassesLoading(true);
    fetch(
      config.apiBaseUrl + `churches/${church.id}/holymasses`,
      DEFAULT_HEADERS
    )
      .then((response) => {
        if (response.ok) return response.json();
        else {
          throw new Error(response.status);
        }
      })
      .then((data) => {
        console.log(data);
        setHolyMasses(data);
        setFutureMasses(data.filter((mass) => dayjs().isBefore(mass.date)));
        console.log(futureMasses);
        setHolyMassesError(false);
      })
      .catch((error) => {
        setHolyMassesError(true);
      })
      .finally(() => {
        setHolyMassesLoading(false);
      });

    setIntentionsLoading(true);
    fetch(
      config.apiBaseUrl + `churches/${church.id}/intentions`,
      DEFAULT_HEADERS
    )
      .then((response) => {
        if (response.ok) return response.json();
        else {
          throw new Error(response.status);
        }
      })
      .then((data) => {
        console.log(data);
        setIntentions(data);
        setIntentionsError(false);
      })
      .catch((error) => {
        setIntentionsError(true);
      })
      .finally(() => {
        setIntentionsLoading(false);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [church.id]);

  const onDateChanged = (val) => {
    const formattedDate = formatDate(val);

    if (!holyMassesLoading && !holyMassesError) {
      let filtred = holyMasses.filter((mass) =>
        mass.date.startsWith(formattedDate)
      );
      setCurrentIntentions(
        intentions.filter((intention) =>
          filtred.map((mass) => mass.id).includes(intention.holyMass.id)
        )
      );
      setCurrentMasses(filtred);
    }
    setSelectedDate(val);
  };

  const renderDay = (date) => {
    const day = date.getDate();
    return (
      <Indicator
        size={6}
        color="green"
        offset={-5}
        disabled={isDateDisabled(date)}
      >
        <div>{day}</div>
      </Indicator>
    );
  };

  const isDateDisabled = (date) => {
    if (holyMasses !== undefined && holyMasses !== null) {
      if (!dayjs().isAfter(date)) {
        for (let i = 0; i < futureMasses.length; i++) {
          if (
            formatDate(date) === futureMasses[i].date &&
            futureMasses[i].availableIntentions > 0
          ) {
            return false;
          }
        }
      }
    }
    return true;
  };

  const getContent = () => {
    if (holyMassesLoading || intentionsLoading)
      return (
        <Center style={{ paddingBottom: rem(110), paddingTop: rem(200) }}>
          <Loader size="xl" />
        </Center>
      );
    else if (holyMassesError || intentionsError)
      return (
        <Center style={{ paddingBottom: rem(350) }}>
          Something went wrong...
        </Center>
      );
    else
      return (
        <>
          <SimpleGrid cols={2}>
            <div>
              <div className={classes.title}>
                <Title order={1}>Kościół {church.name}</Title>
                <Title order={3}>{church.city}</Title>
              </div>
              <DatePicker
                value={selectedDate}
                onChange={onDateChanged}
                renderDay={renderDay}
              />
            </div>
            <div>
              <div style={{ paddingBottom: "30px" }}>
                <Title order={2}>Rezerwacja Intencji</Title>
                {getUser() === null && (
                  <div style={{ paddingBottom: "20px", paddingTop: "20px" }}>
                    <Text c="blue">Zaloguj się, aby zarezerwować intencje</Text>
                  </div>
                )}
                <Table>
                  <thead>
                    <tr>
                      <th>Godzina Mszy</th>
                      <th>Dostępne intencje</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {currentMasses !== null &&
                      currentMasses.map((mass) => (
                        <tr key={mass.id}>
                          <td>{mass.startTime}</td>
                          <td>{mass.availableIntentions}</td>
                          <td>
                            {mass.availableIntentions > 0 &&
                              !dayjs().isAfter(mass.date) && (
                                <Button
                                  disabled={getUser() === null}
                                  onClick={() => setSelectedMass(mass)}
                                >
                                  Zarezerwuj
                                </Button>
                              )}
                          </td>
                        </tr>
                      ))}
                  </tbody>
                </Table>
              </div>
              <div>
                <Title order={2}>Przegląd Intencji</Title>
                {getIntentionsContent()}
              </div>
            </div>
          </SimpleGrid>
          <Modal
            opened={selectedMass !== null}
            onClose={() => {
              setSelectedMass(null);
              setIsIntentionSavingError(false);
            }}
            centered={true}
          >
            {getModalContent()}
          </Modal>
        </>
      );
  };

  const getIntentionsContent = () => {
    if (intentionsLoading) return <Loader size="xl" />;
    else
      return (
        <Table>
          <thead>
            <tr>
              <th>Godzina Mszy</th>
              <th>Intencja</th>
            </tr>
          </thead>
          <tbody>
            {currentIntentions &&
              currentIntentions.map((intention) => (
                <tr key={intention.id}>
                  <td>
                    {currentMasses
                      ? findMassWithId(intention.holyMass.id).startTime
                      : ""}
                  </td>
                  <td>{intention.content}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      );
  };

  const findMassWithId = (id) => {
    return currentMasses.find((mass) => mass.id === id);
  };

  const getModalContent = () => {
    if (isIntentionSaved)
      return (
        <Group position="center">
          <Loader size="xl" />
        </Group>
      );
    else
      return (
        <>
          {isIntentionSavingError && (
            <div style={{ paddingBottom: "20px" }}>
              <Alert title="Błąd" color="red">
                Wystąpił błąd podczas zapisywania intencji. Spróbuj ponownie
              </Alert>
            </div>
          )}
          <div style={{ paddingBottom: "20px" }}>
            <Textarea
              label="Intencja"
              placeholder="Wpisz swoją intencję"
              autosize
              minRows={5}
              maxRows={10}
              value={intentionContent}
              onChange={(event) =>
                setIntentionContent(event.currentTarget.value)
              }
            />
          </div>

          <Button
            onClick={() => {
              const body = {
                content: intentionContent,
                holyMassId: selectedMass.id,
                userId: 1,
                isPaid: false,
              };
              console.log(body);
              setIsIntentionSaved(true);
              fetch(config.apiBaseUrl + "intentions", {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${
                    getUser() === null ? null : getUser().jwtToken
                  }`,
                },
                body: JSON.stringify(body),
              })
                .then((response) => {
                  if (response.status >= 200 && response.status < 300)
                    return response.json();
                  else {
                    throw Error(response.status);
                  }
                })
                .then((data) => {
                  setSelectedMass({
                    ...selectedMass,
                    availableIntentions: selectedMass.availableIntentions - 1,
                  });
                  setHolyMasses(
                    holyMasses.map((mass) =>
                      mass.id === selectedMass.id
                        ? {
                            ...mass,
                            availableIntentions: mass.availableIntentions - 1,
                          }
                        : mass
                    )
                  );
                  setCurrentMasses(
                    currentMasses.map((mass) =>
                      mass.id === selectedMass.id
                        ? {
                            ...mass,
                            availableIntentions: mass.availableIntentions - 1,
                          }
                        : mass
                    )
                  );
                  setSelectedMass(null);
                  setIntentions([data, ...intentions]);
                  setCurrentIntentions([data, ...currentIntentions]);
                })
                .catch((error) => {
                  setIsIntentionSavingError(true);
                })
                .finally(() => {
                  setIsIntentionSaved(false);
                });
            }}
          >
            Zarezerwuj
          </Button>
        </>
      );
  };

  return <Container className={classes.wrapper}>{getContent()}</Container>;
}

export default ChurchIntentionsReservation;
