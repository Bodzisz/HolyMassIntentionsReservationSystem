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
} from "@mantine/core";
import { DatePicker } from "@mantine/dates";

const useStyles = createStyles((theme) => ({
  wrapper: {
    paddingBottom: rem(190),
  },
  title: {
    paddingBottom: rem(50),
  },
}));

const formatDate = (date) => {
  return `${date.getFullYear()}-0${date.getMonth() + 1}-${date.getDate()}`;
};

const DEFAULT_HEADERS = {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
  },
};

function ChurchIntentionsReservation({ church, goBackToChurchList }) {
  const { classes } = useStyles();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [holyMasses, setHolyMasses] = useState(null);
  const [holyMassesLoading, setHolyMassesLoading] = useState(false);
  const [holyMassesError, setHolyMassesError] = useState(false);
  const [currentMasses, setCurrentMasses] = useState(null);
  const [selectedMass, setSelectedMass] = useState(null);
  const [isIntentionSaved, setIsIntentionSaved] = useState(false);
  const [isIntentionSavingError, setIsIntentionSavingError] = useState(false);
  const [intentionContent, setIntentionContent] = useState("");
  const [intentions, setIntentions] = useState(null);
  const [currentIntentions, setCurrentIntentions] = useState(null);
  const [intentionsLoading, setIntentionsLoading] = useState(false);
  const [intentionsError, setIntentionsError] = useState(false);
  const [rereneder, setRerender] = useState(false);

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
        setHolyMasses(data);
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
        setIntentions(data);
        setIntentionsError(false);
      })
      .catch((error) => {
        setIntentionsError(true);
      })
      .finally(() => {
        setIntentionsLoading(false);
      });
  }, [rereneder, church.id]);

  const onDateChanged = (val) => {
    const formattedDate = formatDate(val);

    if (!holyMassesLoading && !holyMassesError) {
      let filtred = holyMasses.filter((mass) =>
        mass.date.startsWith(formattedDate)
      );
      setCurrentIntentions(
        intentions.filter((intention) =>
          filtred.map((mass) => mass.id).includes(intention.holyMass)
        )
      );
      setCurrentMasses(filtred);
    }
    setSelectedDate(val);
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
              <DatePicker value={selectedDate} onChange={onDateChanged} />
            </div>
            <div>
              <div style={{ paddingBottom: "30px" }}>
                <Title order={2}>Rezerwacja Intencji</Title>
                <Table>
                  <thead>
                    <tr>
                      <th>Godzina Mszy</th>
                      <th>Dostępne intencje</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {currentMasses &&
                      currentMasses.map((mass) => (
                        <tr key={mass.id}>
                          <td>{mass.startTime}</td>
                          <td>{mass.availableIntentions}</td>
                          <td>
                            {mass.availableIntentions > 0 && (
                              <Button onClick={() => setSelectedMass(mass)}>
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
                      ? findMassWithId(intention.holyMass).startTime
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
              <Alert title="Internal Server Error" color="red">
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
              setIsIntentionSaved(true);
              fetch(config.apiBaseUrl + "intentions", {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: {
                  content: intentionContent,
                  isPaid: false,
                  holyMass: selectedMass.id,
                  user: 1,
                },
              })
                .then((response) => {
                  if (response.status >= 200 && response.status < 300)
                    return response.json();
                  else {
                    throw Error(response.status);
                  }
                })
                .then((data) => {
                  console.log(data);
                  console.log("SUCCESS");
                  setSelectedMass(null);
                  setRerender(!rereneder);
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