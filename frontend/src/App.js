import "./App.css";
import { MantineProvider, Blockquote, Title } from "@mantine/core";

function App() {
  return (
    <MantineProvider
      withGlobalStyles
      withNormalizeCSS
      theme={{ colorScheme: "light" }}
    >
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          paddingTop: "30px",
        }}
      >
        <Title>Hello there</Title>
        <Blockquote cite="– Mariusz Pudzianowski">
          To by nic nie dało
        </Blockquote>
      </div>
    </MantineProvider>
  );
}

export default App;
