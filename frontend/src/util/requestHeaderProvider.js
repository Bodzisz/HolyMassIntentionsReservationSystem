import { getUser } from "../context/user";

export const getHeaders = () => {
  return getUser() === null
    ? { "Content-Type": "application/json" }
    : {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getUser().jwtToken}`,
      };
};
