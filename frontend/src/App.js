import HomePage from "./components/HomePage";
import OfferingPage from "./components/OfferingPage";
import Footer from "./components/Footer";
import NothingFound from "./components/NothingFound";
import Navigation from "./components/Navigation";
import ChurchList from "./components/ChurchList";
import PriestPanel from "./components/PriestPanel";
import LoginPage from "./components/LoginPage";
import { useState } from "react";
import { getUser } from "./context/user";
import RegisterPage from "./components/RegisterPage";

function App() {
  const [activePage, setActivePage] = useState(1);
  const [user, setUser] = useState(getUser());

  const changeViewToMainPage = () => {
    setActivePage(1);
  };

  const getContent = () => {
    switch (activePage) {
      case 1:
        return <HomePage setActivePage={setActivePage} />;
      case 2:
        return <ChurchList />;
      case 3:
        return <OfferingPage user={user} />;
      case 4:
        return <PriestPanel />;
      case 6:
        return <LoginPage setActivePage={setActivePage} setUser={setUser} />;
      case 7:
        return <RegisterPage setActivePage={setActivePage} />;
      default:
        return <NothingFound goToHomePage={changeViewToMainPage} />;
    }
  };

  return (
    <>
      <Navigation
        activePage={activePage}
        setActivePage={setActivePage}
        user={user}
        setUser={setUser}
      />
      {getContent()}
      <Footer />
    </>
  );
}

export default App;
