import HomePage from "./components/HomePage";
import Footer from "./components/Footer";
import { useState } from "react";
import NothingFound from "./components/NothingFound";
import Navigation from "./components/Navigation";

function App() {
  const [activePage, setActivePage] = useState(1);

  const changeViewToMainPage = () => {
    setActivePage(1);
  };

  const getContent = () => {
    switch (activePage) {
      case 1:
        return <HomePage />;
      default:
        return <NothingFound goToHomePage={changeViewToMainPage} />;
    }
  };

  return (
    <>
      <Navigation activePage={activePage} setActivePage={setActivePage} />
      {getContent()}
      <Footer />
    </>
  );
}

export default App;
