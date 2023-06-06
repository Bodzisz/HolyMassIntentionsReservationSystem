import {
  Center,
  Container,
  Loader,
  Select,
  rem,
  Group,
  NumberInput,
  Button,
  Text,
  Modal,
  Tabs,
  TextInput,
} from "@mantine/core";
import { TimeInput, DatePicker, YearPicker } from "@mantine/dates";
import { useEffect, useRef, useState } from "react";
import useFetch from "../api/useFetch";
import { formatDate } from "../util/dateFormatter";
import { config } from "../config/config";
import { ProgressCardColored } from "./Progress";
import SliderInput from "./OfferingBar";
import { getHeaders } from "../util/requestHeaderProvider";

function PriestPanel() {
  const { data: churches, loading, error } = useFetch("churches");
  const [goals, setGoals] = useState([]);
  const [selectedGoal, setSelectedGoal] = useState(null);
  const [selectedChurch, setSelectedChurch] = useState(null);
  const massStartTimeRef = useRef();
  const goalTitleRef = useRef();
  const goalAmountRef = useRef();
  const [availableIntentions, setAvailableIntentions] = useState(1);
  const [selectedDateOption, setSelectedDateOption] = useState("1");
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [isModalOpened, setModalOpened] = useState(false);
  const [isSaved, setIsSaved] = useState(false);
  const [selectPadding, setSelectPadding] = useState(rem(470));

  useEffect(() => {
    if (selectedChurch === null) {
      getGoals();
      setSelectPadding(rem(470));
    } else {
      setSelectPadding(rem(0));
    }
  }, [selectedChurch]);

  const getGoals = () => {
    fetch("http://localhost:8080/goals", {
      method: "GET",
      headers: getHeaders(),
    })
      .then((response) => response.json())
      .then((data) => {
        setGoals(data);
      })
      .catch((error) => console.error(error));
  };

  const dateOptions = [
    { value: "1", label: "Jeden dzień" },
    { value: "2", label: "Dni powszednie w roku" },
    { value: "3", label: "Niedziele w roku" },
  ];

  const onClickAddMass = (url, body) => {
    if (massStartTimeRef.current.value !== "" && selectedChurch !== null) {
      console.log(body);
      fetch(url, {
        method: "POST",
        headers: getHeaders(),
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
          console.log(data);
          setIsSaved(true);
        })
        .catch(() => {
          setIsSaved(false);
        })
        .finally(() => {
          setModalOpened(true);
        });
    }
  };

  const onClickResetGoal = (url) => {
    console.log(url);
    if (selectedGoal !== null && selectedChurch !== null) {
      fetch(url, {
        method: "PUT",
        headers: getHeaders(),
      })
        .then((data) => {
          console.log(data);
          if (data.status >= 200 && data.status < 300) {
            setIsSaved(true);
            getGoals();
            const Goal = {
              id: selectedGoal.id,
              goal_title: "Dobrobyt parafii",
              amount: 1000,
              gathered: 0,
              parish: selectedGoal.parish,
            };
            setSelectedGoal(Goal);
          } else {
            setIsSaved(false);
          }
        })
        .catch(() => {
          setIsSaved(false);
        })
        .finally(() => {
          setModalOpened(true);
        });
    }
  };

  const onClickUpdateGoal = (url, body) => {
    console.log(body);
    if (selectedGoal !== null && selectedChurch !== null) {
      fetch(url, {
        method: "PUT",
        headers: getHeaders(),
        body: JSON.stringify(body),
      })
        .then((data) => {
          console.log(data);
          if (data.status >= 200 && data.status < 300) {
            setIsSaved(true);
            getGoals();
            const Goal = {
              id: selectedGoal.id,
              goal_title: body.goal_title,
              amount: body.amount,
              gathered: 0,
              parish: selectedGoal.parish,
            };
            setSelectedGoal(Goal);
          } else {
            setIsSaved(false);
          }
        })
        .catch(() => {
          setIsSaved(false);
        })
        .finally(() => {
          setModalOpened(true);
        });
    }
  };

  const getContent = () => {
    if (loading)
      return (
        <div style={{ paddingBottom: rem(110), paddingTop: rem(200) }}>
          <Loader size="xl" />
        </div>
      );
    else if (error !== null) {
      return (
        <Center>
          <div>Something went wrong</div>
        </Center>
      );
    } else {
      return (
        <>
          <Container style={{ paddingBottom: selectPadding }}>
            <Center style={{ paddingBottom: "30px" }}>
              <Select
                label="Wybierz kościół"
                placeholder="Kliknij aby wybrać kościół"
                data={churches.map((church) => ({
                  value: church.id.toString(),
                  label: church.name + " w " + church.city,
                }))}
                value={selectedChurch}
                onChange={(value) => {
                  setSelectedChurch(value);
                  const res = goals.filter((goal) => {
                    return goal.parish.id === churches[value].parish.id;
                  });
                  const [desired] = res;
                  setSelectedGoal(desired);
                }}
                mx="auto"
                style={{ width: "400px" }}
              />
            </Center>
          </Container>

          {selectedChurch && selectedGoal && (
            <Tabs color="teal" defaultValue="first">
              <Tabs.List>
                <Tabs.Tab value="first">Zarządzanie mszami</Tabs.Tab>
                <Tabs.Tab value="second" color="blue">
                  Zarządzanie celem zbiórki
                </Tabs.Tab>
              </Tabs.List>

              <Tabs.Panel value="first" pt="xs">
                <Container>
                  <h1>Dodaj mszę</h1>

                  <Group style={{ paddingBottom: "20px" }}>
                    <TimeInput
                      label="Wpisz godzinę rozpoczęcia mszy"
                      ref={massStartTimeRef}
                      maw={400}
                      mx="auto"
                      required
                    />
                    <NumberInput
                      value={availableIntentions}
                      onChange={(value) => setAvailableIntentions(value)}
                      label="Wpisz liczbę dostępnych intencji"
                      withAsterisk
                      min={0}
                      mx="auto"
                      required
                    />
                  </Group>

                  <Container style={{ paddingBottom: "50px" }}>
                    <Center>
                      <h3>Wybierz datę</h3>
                    </Center>
                    <Center>
                      <Select
                        data={dateOptions}
                        defaultValue={selectedDateOption}
                        value={selectedDateOption}
                        onChange={(value) => setSelectedDateOption(value)}
                        style={{ paddingBottom: "30px" }}
                      />
                    </Center>

                    <Center>{getContentForDateOption()}</Center>
                  </Container>

                  <Group position="right">
                    <Button
                      onClick={() => {
                        if (selectedDateOption === "1") {
                          onClickAddMass(config.apiBaseUrl + "holymasses", {
                            date: formatDate(selectedDate),
                            startTime: massStartTimeRef.current.value + ":00",
                            availableIntentions: availableIntentions,
                            churchId: Number(selectedChurch),
                          });
                        } else {
                          onClickAddMass(
                            `${
                              config.apiBaseUrl
                            }holymasses/addForYear/${selectedYear.getFullYear()}?forSundays=${
                              selectedDateOption === "3"
                            }`,
                            {
                              startTime: massStartTimeRef.current.value + ":00",
                              availableIntentions: availableIntentions,
                              churchId: Number(selectedChurch),
                            }
                          );
                        }
                      }}
                    >
                      Dodaj
                    </Button>
                  </Group>
                </Container>
              </Tabs.Panel>

              <Tabs.Panel value="second" pt="xs">
                <Container>
                  <div>
                    <h3>Aktualna zbiórka:</h3>
                  </div>
                  <div className="progress">
                    <ProgressCardColored
                      current={selectedGoal.gathered}
                      goal={selectedGoal.amount}
                      name={selectedGoal.goal_title}
                    ></ProgressCardColored>
                  </div>
                  <div className="accordion">
                    <Button
                      size="xl"
                      variant="gradient"
                      gradient={{ from: "red", to: "maroon" }}
                      onClick={() =>
                        onClickResetGoal(
                          config.apiBaseUrl + "goals/reset/" + selectedGoal.id
                        )
                      }
                    >
                      Resetuj dane zbiórki
                    </Button>
                  </div>
                  <div>
                    <h3>Edytuj dane zbiórki:</h3>
                  </div>
                  <div className="accordion">
                    <SliderInput
                      minimalOffering={100}
                      label={"Cel pieniężny"}
                      ref={goalAmountRef}
                    ></SliderInput>
                  </div>
                  <div className="accordion">
                    <TextInput
                      placeholder={"Dobrobyt parafii"}
                      label={"Cel zbiórki"}
                      ref={goalTitleRef}
                    ></TextInput>
                  </div>
                  <div className="accordion">
                    <Button
                      size="xl"
                      variant="gradient"
                      gradient={{ from: "yellow", to: "orange" }}
                      onClick={() => {
                        onClickUpdateGoal(
                          config.apiBaseUrl + "goals/" + selectedGoal.id,
                          {
                            goalTitle: goalTitleRef.current.value,
                            amount: parseInt(goalAmountRef.current.value),
                            gathered: 0,
                            parishId: selectedGoal.parish.id,
                          }
                        );
                      }}
                    >
                      Edytuj
                    </Button>
                  </div>
                </Container>
              </Tabs.Panel>
            </Tabs>
          )}
        </>
      );
    }
  };

  const getContentForDateOption = () => {
    if (selectedDateOption === "1") {
      return (
        <DatePicker
          value={selectedDate}
          onChange={(date) => setSelectedDate(date)}
          minDate={new Date()}
        />
      );
    } else if (selectedDateOption === "2" || selectedDateOption === "3") {
      return (
        <Container>
          <Text fz="md" style={{ paddingBottom: "30px" }}>
            Wybierz rok (w obecnym roku wypełnione zostaną tylko dni w
            przyszłości)
          </Text>
          <Center>
            <YearPicker
              value={selectedYear}
              onChange={(year) => setSelectedYear(year)}
              defaultValue={selectedYear}
              minDate={new Date()}
            />
          </Center>
        </Container>
      );
    }
  };

  return (
    <Container>
      {getContent()}
      <Modal
        opened={isModalOpened}
        onClose={() => setModalOpened(false)}
        title={isSaved ? "Sukces" : "Błąd"}
        centered
      >
        {isSaved ? (
          <Text c="teal.4">Zmiany zostały zapisane poprawnie</Text>
        ) : (
          <Text c="red.4">Wystąpił błąd podczas zapisywania zmian</Text>
        )}
      </Modal>
    </Container>
  );
}

export default PriestPanel;
