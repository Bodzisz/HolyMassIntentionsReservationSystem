import "./App.css";
import { MantineProvider, Blockquote } from "@mantine/core";

function App() {
  return (
    <MantineProvider
      withGlobalStyles
      withNormalizeCSS
      theme={{ colorScheme: "light" }}
    >
      <Blockquote
        style={{
          position: "fixed",
          top: "50%",
          left: "50%",
          marginTop: "-50px",
          marginLeft: "-150px",
        }}
        cite="– Mariusz Pudzianowski"
      >
        To by nic nie dało
      </Blockquote>
    </MantineProvider>
  );
}

export default App;
