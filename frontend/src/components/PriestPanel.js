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
} from "@mantine/core";
import { TimeInput, DatePicker, YearPicker } from "@mantine/dates";
import { useEffect, useRef, useState } from "react";
import useFetch from "../api/useFetch";
import { formatDate } from "../util/dateFormatter";
import { config } from "../config/config";
import { ProgressCardColored } from "./Progress"
import { SliderInput } from "./OfferingBar"

function PriestPanel() {
  const { data: churches, loading, error } = useFetch("churches");
  const { data: goals, loadingGoals, errorGoals } = useFetch("goals");
  const [selectedGoal, setSelectedGoal] = useState(null);
  const [selectedChurch, setSelectedChurch] = useState(null);
  const massStartTimeRef = useRef();
  const [availableIntentions, setAvailableIntentions] = useState(1);
  const [selectedDateOption, setSelectedDateOption] = useState("1");
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [isModalOpened, setModalOpened] = useState(false);
  const [isSaved, setIsSaved] = useState(false);
  const [selectPadding, setSelectPadding] = useState(rem(470));

  useEffect(() => {
    if (selectedChurch === null) {
      setSelectPadding(rem(470));
    } else {
      setSelectPadding(rem(0));
    }
  }, [selectedChurch]);

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
        headers: {
          "Content-Type": "application/json",
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

  const getContent = () => {
    if (loading || loadingGoals)
      return (
        <div style={{ paddingBottom: rem(110), paddingTop: rem(200) }}>
          <Loader size="xl" />
        </div>
      );
    else if (error !== null && errorGoals !== null)
    {
      return (
        <Center>
          <div>Something went wrong</div>
        </Center>
      );
    }
      
    else{
      console.log('c');
      console.log(churches);
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
                onChange={
                  (value) => {
                    setSelectedChurch(value);
                    const res = goals.filter(
                      (goal) => {
                        return goal.parish.id === churches[selectedChurch].parish.id;
                      }
                    )
                    const [desired] = res;
                    setSelectedGoal(desired);
                }
                }
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
              <div class="accordion">Wybierz wymiar datku, którym chcesz wesprzeć swoją lokalną społeczność:</div>
                <div class="accordion" ><SliderInput minimalOffering={2}></SliderInput></div>
                <div class="progress" ><ProgressCardColored current={selectedGoal.gathered} goal={selectedGoal.amount} name={selectedGoal.goal_title}></ProgressCardColored></div>
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
          <Text c="teal.4">Msze zostały dodane poprawnie</Text>
        ) : (
          <Text c="red.4">Wystąpił błąd podczas dodawania mszy</Text>
        )}
      </Modal>
    </Container>
  );
}

export default PriestPanel;
