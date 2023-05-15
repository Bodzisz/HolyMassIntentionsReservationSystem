import {
  createStyles,
  Container,
  Title,
  Text,
  Button,
  Group,
  rem,
} from "@mantine/core";
import { Illustration } from "./Illustration";

const useStyles = createStyles((theme) => ({
  root: {
    paddingTop: rem(10),
    paddingBottom: rem(100),
  },

  inner: {
    position: "relative",
  },

  image: {
    ...theme.fn.cover(),
    opacity: 0.75,
  },

  content: {
    paddingTop: rem(220),
    position: "relative",
    zIndex: 1,

    [theme.fn.smallerThan("sm")]: {
      paddingTop: rem(120),
    },
  },

  title: {
    fontFamily: `Greycliff CF, ${theme.fontFamily}`,
    textAlign: "center",
    fontWeight: 900,
    fontSize: rem(38),

    [theme.fn.smallerThan("sm")]: {
      fontSize: rem(32),
    },
  },

  description: {
    maxWidth: rem(540),
    margin: "auto",
    marginTop: theme.spacing.xl,
    marginBottom: `calc(${theme.spacing.xl} * 1.5)`,
  },
}));

export default function NothingFound({ goToHomePage }) {
  const { classes } = useStyles();

  return (
    <Container className={classes.root}>
      <div className={classes.inner}>
        <Illustration className={classes.image} />
        <div className={classes.content}>
          <Title className={classes.title}>Tutaj nic nie ma </Title>
          <Text
            color="dimmed"
            size="lg"
            align="center"
            className={classes.description}
          >
            Zawartość tej strony nie została jeszcze stworzona
          </Text>
          <Group position="center">
            <Button size="md" onClick={() => goToHomePage()}>
              Przejdź na stronę główną
            </Button>
          </Group>
        </div>
      </div>
    </Container>
  );
}
