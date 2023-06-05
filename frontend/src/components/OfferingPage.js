import React, { useState, useEffect, useRef } from "react";
import {
  Container,
  Title,
  Center,
  Select,
  createStyles,
  rem,
  Button,
  Group,
  Text,
} from "@mantine/core";
import { ProgressCardColored } from "./Progress";
import SliderInput from "./OfferingBar";
import { config } from "../config/config";
import { getHeaders } from "../util/requestHeaderProvider";
import { getUser } from "../context/user";

const useStyles = createStyles((theme) => ({
  wrapper: {
    paddingTop: `calc(${theme.spacing.xl} * 2)`,
    paddingBottom: `calc(${theme.spacing.xl})`,
    minHeight: 650,
  },

  title: {
    marginBottom: `calc(${theme.spacing.xl} * 1.5)`,
  },

  item: {
    borderRadius: theme.radius.md,
    marginBottom: theme.spacing.lg,
    border: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[4] : theme.colors.gray[3]
    }`,
  },
}));

const OfferingPage = ({ user }) => {
  const [churches, setChurches] = useState([]);
  const [goals, setGoals] = useState([]);
  const [uniqueCities, setUniqueCities] = useState([]);
  const [selectedGoal, setSelectedGoal] = useState(null);
  const [selectedCity, setSelectedCity] = useState("");
  const [selectedChurch, setSelectedChurch] = useState("");
  const [selectedParish, setSelectedParish] = useState(null);
  const [selectPadding, setSelectPadding] = useState(rem(470));
  const donationSize = useRef();

  useEffect(() => {
    if (selectedCity === null) {
      setSelectPadding(rem(470));
    } else {
      setSelectPadding(rem(0));
    }
  }, [selectedCity, selectedChurch]);

  const getGoals = () => {
    fetch("http://localhost:8080/goals")
      .then((response) => response.json())
      .then((data) => {
        setGoals(data);
      })
      .catch((error) => console.error(error));
  };

  // Fetch the list of churches from the API
  useEffect(() => {
    fetch("http://localhost:8080/churches")
      .then((response) => response.json())
      .then((data) => {
        const cities = [];
        data.forEach((element) => {
          if (!cities.includes(element.city)) {
            cities.push(element.city);
          }
        });
        setChurches(data);
        setUniqueCities(cities);
        getGoals();
      })
      .catch((error) => console.error(error));
  }, []);

  const { classes } = useStyles();

  const sendDonation = (url, body) => {
    console.log(JSON.stringify(body));
    fetch(url, {
      method: "POST",
      headers: getHeaders(),
      body: JSON.stringify(body),
    })
      .then((response) => {
        console.log(response);
        if (response.status >= 200 && response.status < 300)
          return response.json();
        else {
          throw Error(response.status);
        }
      })
      .then((data) => {
        console.log(data);
        const Goal = {
          id: selectedGoal.id,
          goal_title: selectedGoal.goal_title,
          amount: selectedGoal.amount,
          gathered: selectedGoal.gathered + body.amount,
          parish: body.parish,
        };
        setSelectedGoal(Goal);
      });
  };

  return user === null ? (
    <Container>
      <Center style={{ paddingBottom: "10px" }}>
        <Title>Zaloguj się w celu wysłania dotacji na zbiórkę</Title>
      </Center>

      <Text
        color="dimmed"
        size="lg"
        align="center"
        style={{ paddingBottom: rem(450) }}
      >
        Tylko zalogowani uzytkownicy moga wysylac dotacje na zbiorki. Zaloguj
        sie lub zarejestruj, aby móc wspomagać swój kościół.
      </Text>
    </Container>
  ) : (
    <div>
      <Container size="sm" className={classes.wrapper}>
        <Title align="center" className={classes.title}>
          Wirtualna Taca
        </Title>

        <Container style={{ paddingBottom: selectPadding }}>
          <Center style={{ paddingBottom: "30px" }}>
            <Select
              label="Wybierz miasto"
              placeholder="Kliknij aby wybrać miasto"
              data={uniqueCities}
              value={selectedCity}
              onChange={(value) => {
                setSelectedCity(value);
                setSelectedChurch(null);
                setSelectedGoal(null);
              }}
              mx="auto"
              style={{ width: "400px" }}
            />
          </Center>
        </Container>

        <Container style={{ paddingBottom: selectPadding }}>
          <Center style={{ paddingBottom: "30px" }}>
            <Select
              label="Wybierz kościół"
              placeholder="Kliknij aby wybrać kościół"
              data={churches
                .filter(
                  (church) =>
                    church.city === selectedCity || selectedCity === ""
                )
                .map((church) => ({
                  value: church.id.toString(),
                  label: church.name + " w " + church.city,
                }))}
              value={selectedChurch}
              onChange={(value) => {
                setSelectedChurch(value);
                setSelectedParish(churches[value].parish);
                const goalsRes = goals.filter(
                  (goal) => goal.parish.id === churches[value].parish.id
                );
                const [thisGoal] = goalsRes;
                setSelectedGoal(thisGoal);
              }}
              mx="auto"
              style={{ width: "400px" }}
            />
          </Center>
        </Container>

        <div className="accordion">
          Wybierz wymiar datku, którym chcesz wesprzeć swoją lokalną
          społeczność:
        </div>
        <div className="accordion">
          <SliderInput
            minimalOffering={2}
            label={"Wysokość datku"}
            ref={donationSize}
          ></SliderInput>
        </div>
        <div className="progress">
          <ProgressCardColored
            current={
              selectedGoal === undefined || selectedGoal === null
                ? 0
                : selectedGoal.gathered
            }
            goal={
              selectedGoal === undefined || selectedGoal === null
                ? 0
                : selectedGoal.amount
            }
            name={
              selectedGoal === undefined || selectedGoal === null
                ? "Brak zrzutki"
                : selectedGoal.goal_title
            }
          ></ProgressCardColored>
        </div>
        <div className="accordion">
          <Group className={classes.controls}>
            <Button
              size="xl"
              className={classes.control}
              variant="gradient"
              gradient={{ from: "blue", to: "cyan" }}
              onClick={() => {
                sendDonation(config.apiBaseUrl + "donations", {
                  parishId: selectedParish.id,
                  userLogin: getUser().username,
                  amount: parseInt(donationSize.current.value),
                });
                getGoals();
              }}
            >
              Wspomóż wybrany kościół
            </Button>
          </Group>
        </div>
      </Container>
    </div>
  );
};

export default OfferingPage;
