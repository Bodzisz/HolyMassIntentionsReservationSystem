import React, { useState, useEffect } from 'react';
import { Container, Title, Accordion, createStyles, rem, Button, Group, } from '@mantine/core';
import { ProgressCardColored } from './Progress';
import { SliderInput } from './OfferingBar';

const useStyles = createStyles((theme) => ({
    wrapper: {
      paddingTop: `calc(${theme.spacing.xl} * 2)`,
      paddingBottom: `calc(${theme.spacing.xl} * 2)`,
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
  
        <Accordion variant="separated">
          <Accordion.Item className={classes.item} value="city">
            <Accordion.Control>Wybierz miasto parafii</Accordion.Control>
            <Accordion.Panel>Podaj miasto parafii, aby filtrować dostępne wyniki:<br></br>
                <select id="city-select" value={selectedCity} onChange={handleCityChange}>
                <option value="">-- Wybierz --</option>
                {uniqueCities.map((city) => (
                <option value={city}>
                    {city}
                </option>
                ))}
        </select>
            </Accordion.Panel>
          </Accordion.Item>
  
          <Accordion.Item className={classes.item} value="church">
            <Accordion.Control>Wybierz parafię</Accordion.Control>
            <Accordion.Panel>
                Wybierz parafię, którą chcesz wspomóc datkiem:<br></br>        
                <select id="church-select" value={selectedChurch} onChange={handleChurchChange}>
                <option value="">-- Wybierz --</option>
                {churches.filter((church) => church.city===selectedCity || selectedCity==='').map((church) => (
                <option key={church.id} value={church.name}>
                    {church.name}
                </option>
                ))}
                </select>
            </Accordion.Panel>
          </Accordion.Item>
  
          <Accordion.Item className={classes.item} value="donation">
            <Accordion.Control>Określ wysokość ofiary</Accordion.Control>
            <Accordion.Panel>
                <div class="accordion">Wybierz wymiar datku, którym chcesz wesprzeć swoją lokalną społeczność:</div>
                <div class="accordion"><SliderInput minimalOffering={2}></SliderInput></div>
                <div class="accordion"><ProgressCardColored current={1027} goal={3000} name="Chodnik na plebanii"></ProgressCardColored></div>
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
            </Accordion.Panel>
          </Accordion.Item>
        </Accordion>
      </Container>
    </div>
  );
};
  
  export default OfferingPage;
  