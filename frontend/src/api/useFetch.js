import { useEffect, useState } from "react";
import { config } from "../config/config";
import { getHeaders } from "../util/requestHeaderProvider";

function useFetch(url) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setData(null);
    setError(null);

    const DEFAULT_HEADERS = {
      method: "GET",
      headers: getHeaders(),
    };

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
        console.log(url);
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
