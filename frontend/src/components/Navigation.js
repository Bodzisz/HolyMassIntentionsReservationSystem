import {
  createStyles,
  Title,
  Box,
  Header,
  Group,
  rem,
  Button,
  Burger,
  Drawer,
  ScrollArea,
  Divider,
  Text,
} from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { getUser, logoutUser } from "../context/user";
import { useEffect } from "react";

const useStyles = createStyles((theme) => ({
  link: {
    display: "flex",
    alignItems: "center",
    height: "100%",
    paddingLeft: theme.spacing.md,
    paddingRight: theme.spacing.md,
    textDecoration: "none",
    color: theme.colorScheme === "dark" ? theme.white : theme.black,
    fontWeight: 500,
    fontSize: theme.fontSizes.sm,

    [theme.fn.smallerThan("sm")]: {
      height: rem(42),
      display: "flex",
      alignItems: "center",
      width: "100%",
    },

    ...theme.fn.hover({
      backgroundColor:
        theme.colorScheme === "dark"
          ? theme.colors.dark[6]
          : theme.colors.gray[0],
    }),
  },

  subLink: {
    width: "100%",
    padding: `${theme.spacing.xs} ${theme.spacing.md}`,
    borderRadius: theme.radius.md,

    ...theme.fn.hover({
      backgroundColor:
        theme.colorScheme === "dark"
          ? theme.colors.dark[7]
          : theme.colors.gray[0],
    }),

    "&:active": theme.activeStyles,
  },

  dropdownFooter: {
    backgroundColor:
      theme.colorScheme === "dark"
        ? theme.colors.dark[7]
        : theme.colors.gray[0],
    margin: `calc(${theme.spacing.md} * -1)`,
    marginTop: theme.spacing.sm,
    padding: `${theme.spacing.md} calc(${theme.spacing.md} * 2)`,
    paddingBottom: theme.spacing.xl,
    borderTop: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[1]
    }`,
  },

  hiddenMobile: {
    [theme.fn.smallerThan("sm")]: {
      display: "none",
    },
  },

  hiddenDesktop: {
    [theme.fn.largerThan("sm")]: {
      display: "none",
    },
  },
}));

const contentPages = [
  {
    id: 1,
    title: "Strona główna",
    roles: [],
  },
  {
    id: 2,
    title: "Kościoły",
    roles: [],
  },
  {
    id: 3,
    title: "Wirtualna Taca",
    roles: [],
  },
  {
    id: 4,
    title: "Panel Księdza",
    roles: ["PRIEST"],
  },
  {
    id: 5,
    title: "Kontakt",
    roles: [],
  },
];

function Navigation({ activePage, setActivePage, user, setUser }) {
  const { classes, theme } = useStyles();
  const [drawerOpened, { toggle: toggleDrawer, close: closeDrawer }] =
    useDisclosure(false);

  useEffect(() => {}, [user]);

  const onClickLogin = () => {
    setActivePage(6);
  };

  const onClickRegister = () => {
    setActivePage(7);
  };

  return (
    <Box pb={120}>
      <Header height={60} px={"md"}>
        <Group position="apart" sx={{ height: "100%" }}>
          <Group>
            <img src="./church_logo.png" alt="Church Logo" height={40} />
            <Title>Rezerwacja Intencji</Title>
          </Group>

          <Group
            sx={{ height: "100%" }}
            spacing={0}
            className={classes.hiddenMobile}
          >
            {contentPages.map((page) => {
              if (
                page.roles.length === 0 ||
                (user !== null && page.roles.includes(user.role))
              ) {
                return (
                  <a
                    href="/#"
                    onClick={() => setActivePage(page.id)}
                    key={page.id}
                    className={classes.link}
                    style={{
                      color:
                        activePage === page.id ? theme.colors.blue[5] : null,
                    }}
                  >
                    <Text>{page.title}</Text>
                  </a>
                );
              } else return null;
            })}
          </Group>

          <Group className={classes.hiddenMobile}>
            {user === null ? (
              <>
                <Button variant="default" onClick={onClickLogin}>
                  Logowanie
                </Button>
                <Button onClick={onClickRegister}>Rejestracja</Button>
              </>
            ) : (
              <>
                <Text color="black">
                  {user.firstName + " " + user.lastName}
                </Text>
                <Button
                  onClick={() => {
                    logoutUser();
                    setUser(getUser());
                    setActivePage(1);
                  }}
                >
                  Wyloguj
                </Button>
              </>
            )}
          </Group>

          <Burger
            opened={drawerOpened}
            onClick={toggleDrawer}
            className={classes.hiddenDesktop}
          />
        </Group>
      </Header>

      <Drawer
        opened={drawerOpened}
        onClose={closeDrawer}
        size="100%"
        padding="md"
        title="Navigation"
        className={classes.hiddenDesktop}
        zIndex={1000000}
      >
        <ScrollArea h={`calc(100vh - ${rem(60)})`} mx="-md">
          <Divider
            my="sm"
            color={theme.colorScheme === "dark" ? "dark.5" : "gray.1"}
          />

          {contentPages.map((page) => (
            <a
              href="/#"
              onClick={() => {
                setActivePage(page.id);
                closeDrawer();
              }}
              key={page.id}
              className={classes.link}
              style={{
                color: activePage === page.id ? theme.colors.blue[5] : null,
              }}
            >
              <Text>{page.title}</Text>
            </a>
          ))}

          <Divider
            my="sm"
            color={theme.colorScheme === "dark" ? "dark.5" : "gray.1"}
          />

          <Group position="center" grow pb="xl" px="md">
            {user === null ? (
              <>
                <Button variant="default" onClick={onClickLogin}>
                  Logowanie
                </Button>
                <Button onClick={onClickRegister}>Rejestracja</Button>
              </>
            ) : (
              <>
                <Text color="black">
                  {user.firstName + " " + user.lastName}
                </Text>
                <Button
                  onClick={() => {
                    logoutUser();
                    setUser(getUser());
                    setActivePage(1);
                  }}
                >
                  Wyloguj
                </Button>
              </>
            )}
          </Group>
        </ScrollArea>
      </Drawer>
    </Box>
  );
}

export default Navigation;
