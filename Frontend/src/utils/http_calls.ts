const burl = true ? "/api/v1" : "http://localhost:8080/api/v1";

export const getUsers = async () => {
  let users = await fetch(burl + "/users")
    .then((e) => {
      return e.json();
    })
    .catch((e) => {
      console.log(e);
      return [];
    });
  return users;
};

export const updateUserTitle = async (id: string, title: string) => {
  try {
    const rawResponse = await fetch(burl + "/users/" + id, {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ title }),
    });
    const trueRes = await rawResponse.json();
    console.log('====================================');
    console.log(trueRes);
    console.log('====================================');
    return true;
  } catch (error) {
    console.log('====================================');
    console.log(error);
    console.log('====================================');
    return false;
  }
};
