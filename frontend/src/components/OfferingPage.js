import React, { useState, useEffect } from 'react';
import { Container, Title, Center, Select, createStyles, rem, Button, Group, } from '@mantine/core';
import { ProgressCardColored } from './Progress';
import { SliderInput } from './OfferingBar';  

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
  const [donationAmount, setDonationAmount] = useState(0);
  const [churches, setChurches] = useState([]);
  const [uniqueCities, setUniqueCities] = useState([]);
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedChurch, setSelectedChurch] = useState('');
  const [expandedItem, setExpandedItem] = useState('city');
  const [selectPadding, setSelectPadding] = useState(rem(470));
  
  useEffect(() => {
    if (selectedCity === null) {
      setSelectPadding(rem(470));
    } else {
      setSelectPadding(rem(0));
    }
  }, [selectedChurch]);

  // Function to handle donation submission
  const handleDonationSubmit = (e) => {
    e.preventDefault();
    // Logic to handle donation submission, update donationAmount state, etc.
    // For simplicity, let's assume the newDonationAmount variable holds the updated donation amount

    // Update the donationAmount state with the new value
    setDonationAmount(donationAmount);
  };

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
        })
      .catch((error) => console.error(error));
  }, []);

  // Handle the church selection change
  const handleChurchChange = (event) => {
    setSelectedChurch(event.target.value);
  };

  const handleCityChange = (event) => {
    const selectedCityValue = event.target.value;
    setSelectedCity(selectedCityValue);
    setSelectedChurch('');
    console.log(selectedCityValue);
  };

  const { classes } = useStyles();

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
                onChange={(value) => setSelectedChurch(value)}
                mx="auto"
                style={{ width: "400px" }}
              />
            </Center>
          </Container>

                <div class="accordion">Wybierz wymiar datku, którym chcesz wesprzeć swoją lokalną społeczność:</div>
                <div class="accordion" ><SliderInput minimalOffering={2}></SliderInput></div>
                <div class="progress" ><ProgressCardColored current={1027} goal={3000} name="Chodnik na plebanii"></ProgressCardColored></div>
                <div class="accordion">
                    <Group className={classes.controls}>
                        <Button
                            size="xl"
                            className={classes.control}
                            variant="gradient"
                            gradient={{ from: "blue", to: "cyan" }}
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
  