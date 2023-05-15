import { createStyles, Container, Text, rem } from "@mantine/core";

const useStyles = createStyles((theme) => ({
  footer: {
    marginTop: rem(120),
    borderTop: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[2]
    }`,
  },

  inner: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    paddingTop: theme.spacing.xl,
    paddingBottom: theme.spacing.xl,

    [theme.fn.smallerThan("xs")]: {
      flexDirection: "column",
    },
  },

  footerElement: {
    paddingLeft: theme.spacing.md,
    paddingRight: theme.spacing.md,
  },

  footerText: {
    marginTop: theme.spacing.xl,
    fontSize: rem(14),

    [theme.fn.smallerThan("sm")]: {
      fontSize: rem(18),
    },
  },
}));

function Footer() {
  const { classes } = useStyles();

  return (
    <div className={classes.footer}>
      <Container className={classes.inner}>
        <img
          src="./church_logo.png"
          alt="Church Logo"
          height={30}
          className={classes.footerElement}
        />
        <Text
          color="dimmed"
          classNames={[classes.footerElement, classes.footerText]}
        >
          Kacper Wójcicki & Mateusz Woźniak
        </Text>
      </Container>
    </div>
  );
}

export default Footer;
