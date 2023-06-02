import React, { useState, useEffect, useRef } from 'react';
import { Container, Title, Center, Select, createStyles, rem, Button, Group, } from '@mantine/core';
import { ProgressCardColored } from './Progress';
import SliderInput from './OfferingBar';  
import { config } from "../config/config";


const User = {
    "id": 1
};

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
        theme.colorScheme === 'dark' ? theme.colors.dark[4] : theme.colors.gray[3]
      }`,
    },
  }));

const OfferingPage = () => {
  const [churches, setChurches] = useState([]);
  const [goals, setGoals] = useState([]);
  const [uniqueCities, setUniqueCities] = useState([]);
  const [selectedGoal, setSelectedGoal] = useState(null);
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedChurch, setSelectedChurch] = useState('');
  const [selectedParish, setSelectedParish] = useState(null);
  const [selectPadding, setSelectPadding] = useState(rem(470));
  const donationSize = useRef();
  
  useEffect(() => {
    if (selectedCity === null) {
      setSelectPadding(rem(470));
    } else {
      setSelectPadding(rem(0));
    }
  }, [selectedChurch]);

  const getGoals = (() => {
    fetch('http://localhost:8080/goals')
    .then((response) => response.json())
    .then((data) => {
          setGoals(data);
      })
    .catch((error) => console.error(error));
  })

   // Fetch the list of churches from the API
   useEffect(() => {
    fetch('http://localhost:8080/churches')
      .then((response) => response.json())
      .then((data) => {
            const cities = [];
            data.forEach(element => {
                if (!cities.includes(element.city))
                    {cities.push(element.city);}
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
      headers: {
        "Content-Type": "application/json",
      },
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
        setIsSaved(true);
        const Goal = {
          id: selectedGoal.id,
          goal_title: selectedGoal.goal_title,
          amount: selectedGoal.amount,
          gathered: selectedGoal.gathered + body.amount,
          parish: body.parish
        }
        setSelectedGoal(Goal);
      })
      .catch(() => {
        setIsSaved(false);
      })
      .finally(() => {
        setModalOpened(true);
      });
  }

  return (
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
                data={churches.filter((church) => church.city===selectedCity || selectedCity==='').map((church) => ({
                  value: church.id.toString(),
                  label: church.name + " w " + church.city,
                }))}
                value={selectedChurch}
                onChange={(value) => {
                  setSelectedChurch(value);
                  setSelectedParish(churches[value].parish)
                  const goalsRes = goals.filter((goal) => goal.parish.id === churches[value].parish.id);
                  const [thisGoal] = goalsRes;
                  setSelectedGoal(thisGoal);
                }}
                mx="auto"
                style={{ width: "400px" }}
              />
            </Center>
          </Container>

                <div className="accordion">Wybierz wymiar datku, którym chcesz wesprzeć swoją lokalną społeczność:</div>
                <div className="accordion" ><SliderInput minimalOffering={2} label={"Wysokość datku"} ref={donationSize}></SliderInput></div>
                <div className="progress" ><ProgressCardColored 
                  current={selectedGoal===undefined || selectedGoal===null ? 0 : selectedGoal.gathered} goal={selectedGoal===undefined|| selectedGoal===null ? 0 : selectedGoal.amount}
                  name={selectedGoal===undefined|| selectedGoal===null ? 'Brak zrzutki' : selectedGoal.goal_title}></ProgressCardColored></div>
                <div className="accordion">
                    <Group className={classes.controls}>
                        <Button size="xl" className={classes.control} variant="gradient" gradient={{ from: "blue", to: "cyan" }}
                         onClick={() => {
                          sendDonation(
                            config.apiBaseUrl + "donations",
                            {
                              user: User,
                              parish: selectedParish,
                              amount: parseInt(donationSize.current.value)
                            }
                          );
                          getGoals();
                         }}>
                            Wspomóż wybrany kościół
                            
                        </Button>
                    </Group>
                </div>
      </Container>
    </div>
  );
};
  
  export default OfferingPage;
  