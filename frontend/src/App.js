import HomePage from "./components/HomePage";
import Footer from "./components/Footer";
import { useState } from "react";
import NothingFound from "./components/NothingFound";
import Navigation from "./components/Navigation";
import ChurchList from "./components/ChurchList";
import PriestPanel from "./components/PriestPanel";

function App() {
  const [activePage, setActivePage] = useState(1);

  const changeViewToMainPage = () => {
    setActivePage(1);
  };

  const getContent = () => {
    switch (activePage) {
      case 1:
        return <HomePage setActivePage={setActivePage} />;
      case 2:
        return <ChurchList />;
      case 4:
        return <PriestPanel />;
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
