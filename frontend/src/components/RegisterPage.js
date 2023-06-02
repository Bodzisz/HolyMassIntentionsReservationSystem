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
  Radio,
} from "@mantine/core";
import { useState } from "react";
import { config } from "../config/config";
import { getHeaders } from "../util/requestHeaderProvider";

function RegisterPage({ setActivePage }) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");

  const onClickRegister = () => {
    const body = JSON.stringify({ firstName, lastName, login, password, role });
    console.log(body);
    fetch(config.apiBaseUrl + "users", {
      method: "POST",
      headers: getHeaders(),
      body: body,
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300)
          return response.json();
        else {
          throw Error(response.status);
        }
      })
      .then(() => {
        setActivePage(6);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container size={420} my={10} style={{ paddingBottom: rem(160) }}>
      <Title
        align="center"
        sx={(theme) => ({
          fontFamily: `Greycliff CF, ${theme.fontFamily}`,
          fontWeight: 900,
        })}
      >
        Rejestracja
      </Title>
      <Text color="dimmed" size="sm" align="center" mt={5}>
        Masz juz konto?{" "}
        <Anchor size="sm" component="button" onClick={() => setActivePage(6)}>
          Zaguj się teraz
        </Anchor>
      </Text>

      <Paper withBorder shadow="md" p={30} mt={10} radius="md">
        <Radio.Group
          value={role}
          onChange={setRole}
          name="Typ konta"
          label="Wybierz rolę dla Twojego konta"
          withAsterisk
        >
          <Radio mt="sm" value="USER" label="Uzytkownik" />
          <Radio mt="sm" value="PRIEST" label="Ksiądz" />
        </Radio.Group>
        <TextInput
          label="Imię"
          placeholder="Wpisz twoje imię"
          value={firstName}
          onChange={(event) => setFirstName(event.currentTarget.value)}
          required
          mt="md"
        />
        <TextInput
          label="Nazwisko"
          placeholder="Wpisz twoje nazwisko"
          value={lastName}
          onChange={(event) => setLastName(event.currentTarget.value)}
          required
          mt="md"
        />
        <TextInput
          label="Login"
          placeholder="Wpisz twój login"
          value={login}
          onChange={(event) => setLogin(event.currentTarget.value)}
          required
          mt="md"
        />
        <PasswordInput
          label="Hasło"
          placeholder="Wpisz twoje hasło"
          required
          mt="md"
          value={password}
          onChange={(event) => setPassword(event.currentTarget.value)}
        />
        <Button onClick={onClickRegister} fullWidth mt="xl">
          Zarejestruj
        </Button>
      </Paper>
    </Container>
  );
}

export default RegisterPage;
