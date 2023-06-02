import {
  TextInput,
  PasswordInput,
  Anchor,
  Paper,
  Title,
  Text,
  Container,
  Button,
  rem,
} from "@mantine/core";
import { useState } from "react";
import { config } from "../config/config";
import { setUser as setLocalStorageUser } from "../context/user";

function LoginPage({ setActivePage, setUser }) {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  const handleLoginClick = () => {
    fetch(config.apiBaseUrl + "auth/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify({
        login: login,
        password: password,
      }),
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300)
          return response.json();
        else {
          throw Error(response.status);
        }
      })
      .then((data) => {
        const user = {
          username: data.username,
          firstName: data.firstName,
          lastName: data.lastName,
          jwtToken: data.token,
          role: data.role,
        };
        setUser(user);
        setLocalStorageUser(user);
        setActivePage(1);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container size={420} my={40} style={{ paddingBottom: rem(160) }}>
      <Title
        align="center"
        sx={(theme) => ({
          fontFamily: `Greycliff CF, ${theme.fontFamily}`,
          fontWeight: 900,
        })}
      >
        Witamy z powrotem!
      </Title>
      <Text color="dimmed" size="sm" align="center" mt={5}>
        Nie masz jeszcze konta?{" "}
        <Anchor size="sm" component="button" onClick={() => setActivePage(7)}>
          Załóz je teraz
        </Anchor>
      </Text>

      <Paper withBorder shadow="md" p={30} mt={30} radius="md">
        <TextInput
          label="Login"
          placeholder="Wpisz twój login"
          value={login}
          onChange={(event) => setLogin(event.currentTarget.value)}
          required
        />
        <PasswordInput
          label="Hasło"
          placeholder="Wpisz twoje hasło"
          required
          mt="md"
          value={password}
          onChange={(event) => setPassword(event.currentTarget.value)}
        />
        <Button fullWidth mt="xl" onClick={handleLoginClick}>
          Zaloguj
        </Button>
      </Paper>
    </Container>
  );
}

export default LoginPage;
