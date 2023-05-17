import {
  createStyles,
  rem,
  Group,
  Text,
  ScrollArea,
  SimpleGrid,
  Card,
  Image,
  Autocomplete,
} from "@mantine/core";
import useFetch from "../api/useFetch";
import { Loader } from "@mantine/core";
import { useState } from "react";
import ChurchIntentionsReservation from "./ChurchIntentionsReservation";

const useStyles = createStyles((theme) => ({
  wrapper: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    height: "100%",
    paddingTop: 0,
    paddingBottom: rem(195),
  },
  link: {
    textDecoration: "none",
    color: theme.colorScheme === "dark" ? theme.white : theme.black,
    fontWeight: 500,
    fontSize: theme.fontSizes.sm,

    [theme.fn.smallerThan("sm")]: {
      height: rem(42),
    },

    ...theme.fn.hover({
      backgroundColor:
        theme.colorScheme === "dark"
          ? theme.colors.dark[6]
          : theme.colors.gray[0],
    }),
  },
  tableElement: {
    cursor: "pointer",
  },
}));

const cities = ["Wrocław", "Głuszyca", "Wałbrzych", "Jelenia Góra"];

function ChurchList() {
  const { classes } = useStyles();
  const { data: churchData, loading, error } = useFetch("churches");
  const [cityFilter, setCityFilter] = useState("");
  const [selectedChurch, setSelectedChurch] = useState(null);

  const getContent = () => {
    if (loading)
      return (
        <div style={{ paddingBottom: rem(110), paddingTop: rem(200) }}>
          <Loader size="xl" />
        </div>
      );
    else if (error !== null) return <div>Something went wrong</div>;
    else if (selectedChurch !== null)
      return (
        <ChurchIntentionsReservation
          church={findChurchById(selectedChurch)}
          goBackToChurchList={goBackToChurchList}
        />
      );
    else return getChurchList();
  };

  const findChurchById = (id) => {
    return churchData.find((church) => church.id === id);
  };

  const goBackToChurchList = () => {
    setSelectedChurch(null);
  };

  const getRows = () => {
    if (churchData !== null && churchData !== undefined) {
      return churchData
        .filter((church) => {
          if (cityFilter === "") return true;
          else {
            return church.city.startsWith(cityFilter);
          }
        })
        .map((church) => (
          <Card
            shadow="sm"
            padding="lg"
            radius="md"
            key={church.id}
            href="/#"
            withBorder
            className={classes.tableElement}
            onClick={() => setSelectedChurch(church.id)}
          >
            <Card.Section>
              <Image
                src="./church_building.jpeg"
                height={160}
                alt={church.name}
                style={{ paddingBottom: "20px" }}
              />
            </Card.Section>
            <Group position="apart" mt="md" mb="xs">
              <Text weight={500}>{church.name}</Text>
            </Group>

            <Text size="sm" color="dimmed">
              {church.city}
            </Text>
          </Card>
        ));
    }
    return [];
  };

  const getChurchList = () => {
    return (
      <>
        <Autocomplete
          label="Type your city"
          placeholder="Start typing ..."
          data={cities}
          value={cityFilter}
          onChange={(value) => setCityFilter(value)}
          style={{ paddingBottom: "40px" }}
        />
        <ScrollArea>
          <SimpleGrid cols={3} spacing="xl">
            {getRows()}
          </SimpleGrid>
        </ScrollArea>
      </>
    );
  };

  return (
    <div className={selectedChurch === null ? classes.wrapper : null}>
      {getContent()}
    </div>
  );
}

export default ChurchList;
