import { useEffect, useState } from "react";
import { config } from "../config/config";

function useFetch(url, object) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const DEFAULT_HEADERS = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(object),
  };

  useEffect(() => {
    setLoading(true);
    setData(null);
    setError(null);

    fetch(config.apiBaseUrl + url, DEFAULT_HEADERS)
      .then((response) => {
        if (response.ok) return response.json();
        else {
          setError(response.status);
          throw Error(response.status);
        }
      })
      .then((data) => {
        console.log(data);
        setData(data);
        setError(null);
      })
      .catch((error) => {
        setError(error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [url]);

  return { data, loading, error };
}

export default useFetch;
