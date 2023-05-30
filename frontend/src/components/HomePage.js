import {
  createStyles,
  Container,
  Text,
  Button,
  Group,
  rem,
} from "@mantine/core";

const useStyles = createStyles((theme) => ({
  wrapper: {
    position: "relative",
    boxSizing: "border-box",
    backgroundColor:
      theme.colorScheme === "dark" ? theme.colors.dark[8] : theme.white,
  },

  inner: {
    position: "relative",
    paddingBottom: rem(80),

    [theme.fn.smallerThan("sm")]: {
      paddingBottom: rem(80),
      paddingTop: rem(80),
    },
  },

  title: {
    fontFamily: `Greycliff CF, ${theme.fontFamily}`,
    fontSize: rem(62),
    fontWeight: 900,
    lineHeight: 1.1,
    margin: 0,
    padding: 0,
    color: theme.colorScheme === "dark" ? theme.white : theme.black,

    [theme.fn.smallerThan("sm")]: {
      fontSize: rem(42),
      lineHeight: 1.2,
    },
  },

  description: {
    marginTop: theme.spacing.xl,
    fontSize: rem(24),

    [theme.fn.smallerThan("sm")]: {
      fontSize: rem(18),
    },
  },

  controls: {
    marginTop: `calc(${theme.spacing.xl} * 2)`,

    [theme.fn.smallerThan("sm")]: {
      marginTop: theme.spacing.xl,
    },
  },

  control: {
    height: rem(54),
    paddingLeft: rem(38),
    paddingRight: rem(38),

    [theme.fn.smallerThan("sm")]: {
      height: rem(54),
      paddingLeft: rem(18),
      paddingRight: rem(18),
      flex: 1,
    },
  },
}));

function HomePage({ setActivePage }) {
  const { classes } = useStyles();

  return (
    <div className={classes.wrapper}>
      <Container size={1200} className={classes.inner}>
        <h1 className={classes.title}>
          Zdalny{" "}
          <Text
            component="span"
            variant="gradient"
            gradient={{ from: "blue", to: "cyan" }}
            inherit
          >
            System Rezerwacji Intencji
          </Text>{" "}
          Mszy Świętych we kościołach w całej Polsce
        </h1>

        <Text className={classes.description} color="dimmed">
          Przeglądaj intencje i wolne terminy intencji mszy świętych w Twoim
          Kościele, następnie zarezerwuj intencje mszy świętej w dowolnym
          kościele w Polsce nie wychodząc z domu
        </Text>

        <Group className={classes.controls}>
          <Button
            size="xl"
            className={classes.control}
            variant="gradient"
            gradient={{ from: "blue", to: "cyan" }}
            onClick={() => setActivePage(2)}
          >
            Znajdź Twój Kościół
          </Button>

          <Button
            component="a"
            href="/#"
            size="xl"
            variant="default"
            className={classes.control}
            onClick={() => setActivePage(3)}
          >
            Wspomóż nas
          </Button>
        </Group>
      </Container>
    </div>
  );
}

export default HomePage;
