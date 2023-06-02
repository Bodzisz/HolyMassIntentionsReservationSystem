import { localStorageKeys } from "../config/config";

export const getUser = () => {
  if (localStorage.getItem(localStorageKeys.jwtToken) === null) return null;
  else {
    return {
      username: localStorage.getItem(localStorageKeys.username),
      firstName: localStorage.getItem(localStorageKeys.firstName),
      lastName: localStorage.getItem(localStorageKeys.lastName),
      jwtToken: localStorage.getItem(localStorageKeys.jwtToken),
      role: localStorage.getItem(localStorageKeys.role),
    };
  }
};

export const setUser = (user) => {
  localStorage.setItem(localStorageKeys.username, user.username);
  localStorage.setItem(localStorageKeys.firstName, user.firstName);
  localStorage.setItem(localStorageKeys.lastName, user.lastName);
  localStorage.setItem(localStorageKeys.jwtToken, user.jwtToken);
  localStorage.setItem(localStorageKeys.role, user.role);
};

export const logoutUser = () => {
  localStorage.removeItem(localStorageKeys.username);
  localStorage.removeItem(localStorageKeys.firstName);
  localStorage.removeItem(localStorageKeys.lastName);
  localStorage.removeItem(localStorageKeys.jwtToken);
  localStorage.removeItem(localStorageKeys.role);
};

export const getUserRole = () => {
  if (localStorage.getItem(localStorageKeys.jwtToken) === null) return null;
  else {
    return localStorageKeys.getItem(localStorageKeys.role);
  }
};
