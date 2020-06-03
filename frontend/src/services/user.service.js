

export const getUsers = () => {
  let URL = "https://jsonplaceholder.typicode.com/users";
  return fetch(URL).then(response => response.json());
};