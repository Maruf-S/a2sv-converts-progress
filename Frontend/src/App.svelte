<script lang="ts">
  import { onMount, onDestroy } from "svelte";
  import { fly } from "svelte/transition";
  import Toast from "./components/Toast.svelte";
  // export let name: string;
  import { Users } from "./store/store";
  import { getUsers, updateUserTitle } from "./utils/http_calls";
  import { notifications } from "./utils/notifications";
  import dayjs from "dayjs";
  import relativeTime from "dayjs/plugin/relativeTime";

  dayjs.extend(relativeTime as any);
  let loadings = [];
  let emojis = []
  const populate = async () => {
    const res = await getUsers();
    loadings = new Array(res.length).fill(false);
    emojis = res.map(()=>randomEmoji());
    console.log(res);
    
    res.sort(function (a, b) {
      return a.totalCount > b.totalCount ? -1 : 1;
    });
    Users.update((current: any) => {
      return res;
    });
  };

  onMount(async () => {
    await populate();
  });
  const handleUpdateTitle = (e: any) => {
    const formData = new FormData(e.target);
    const data: any = {};
    for (let field of formData) {
      const [key, value] = field;
      data[key] = value;
    }
    loadings[parseInt(data["index"])] = true;
    const logic = () => {
      if (data.title.length < 5) {
        return notifications.error(
          "Please enter a minimum of 5 charachters",
          3000
        );
      }
      const res = updateUserTitle(data.id, data.title);
      if (res) {
        notifications.success("Title updated successfuly", 3000);
      } else {
        return notifications.error("Unknown error occoured", 3000);
      }
    };
    logic();
    loadings[parseInt(data["index"])] = false;
  };
  const getColorSubmission = (acc, total) => {
    const val = acc / total;
    if (val > 0.6) {
      return "green";
    }
    if (val > 0.4) {
      return "yellow";
    }
    if (val > 0.2) {
      return "red";
    }
  };
  const emojisArray = [
    "😀",
    "😉",
    "😊",
    "😁",
    "😣",
    "😌",
    "😒",
    "😔",
    "😳",
    "😵",
    "😎",
    "🤓",
    "🤓",
  ];
  const randomEmoji = () =>
    emojisArray[Math.floor(Math.random() * emojisArray.length)];

  let isLight = true;
  const handleThemeChange = () => {
    if (isLight) {
      isLight = false;
    } else {
      isLight = true;
    }
  };
</script>

<div data-theme={isLight ? "dark" : "wireframe"} class="min-h-screen">
  <Toast />
  <div class="absolute top-0 right-0 m-2">
    <label class="swap swap-rotate">
      <!-- this hidden checkbox controls the state -->
      <input type="checkbox" on:change={handleThemeChange} />

      <!-- sun icon -->
      <svg
        class="swap-on fill-current w-10 h-10"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        ><path
          d="M5.64,17l-.71.71a1,1,0,0,0,0,1.41,1,1,0,0,0,1.41,0l.71-.71A1,1,0,0,0,5.64,17ZM5,12a1,1,0,0,0-1-1H3a1,1,0,0,0,0,2H4A1,1,0,0,0,5,12Zm7-7a1,1,0,0,0,1-1V3a1,1,0,0,0-2,0V4A1,1,0,0,0,12,5ZM5.64,7.05a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29,1,1,0,0,0,0-1.41l-.71-.71A1,1,0,0,0,4.93,6.34Zm12,.29a1,1,0,0,0,.7-.29l.71-.71a1,1,0,1,0-1.41-1.41L17,5.64a1,1,0,0,0,0,1.41A1,1,0,0,0,17.66,7.34ZM21,11H20a1,1,0,0,0,0,2h1a1,1,0,0,0,0-2Zm-9,8a1,1,0,0,0-1,1v1a1,1,0,0,0,2,0V20A1,1,0,0,0,12,19ZM18.36,17A1,1,0,0,0,17,18.36l.71.71a1,1,0,0,0,1.41,0,1,1,0,0,0,0-1.41ZM12,6.5A5.5,5.5,0,1,0,17.5,12,5.51,5.51,0,0,0,12,6.5Zm0,9A3.5,3.5,0,1,1,15.5,12,3.5,3.5,0,0,1,12,15.5Z"
        /></svg
      >

      <!-- moon icon -->
      <svg
        class="swap-off fill-current w-10 h-10"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        ><path
          d="M21.64,13a1,1,0,0,0-1.05-.14,8.05,8.05,0,0,1-3.37.73A8.15,8.15,0,0,1,9.08,5.49a8.59,8.59,0,0,1,.25-2A1,1,0,0,0,8,2.36,10.14,10.14,0,1,0,22,14.05,1,1,0,0,0,21.64,13Zm-9.5,6.69A8.14,8.14,0,0,1,7.08,5.22v.27A10.15,10.15,0,0,0,17.22,15.63a9.79,9.79,0,0,0,2.1-.22A8.11,8.11,0,0,1,12.14,19.73Z"
        /></svg
      >
    </label>
  </div>
  <!-- Put this part before </body> tag -->
  {#each $Users as user, idx ("F" + user.id)}
    <div>
      <form on:submit|preventDefault={handleUpdateTitle}>
        <input type="checkbox" id="my-modal-4{user.id}" class="modal-toggle" />
        <label for="my-modal-4{user.id}" class="modal cursor-pointer">
          <label class="modal-box relative" for="">
            <h3 class="text-lg font-bold">Edit {user.name}'s title?</h3>
            <div class="form-control w-full max-w-xs">
              <!-- svelte-ignore a11y-label-has-associated-control -->
              <label class="label">
                <span class="label-text">Enter new title</span>
              </label>
              <input
                type="text"
                maxlength="25"
                minlength="5"
                name="title"
                class="input input-bordered no-animation w-full max-w-xs"
                bind:value={$Users[idx].title}
              />
              <input type="hidden" name="id" value={user.id} />
              <input type="hidden" name="index" value={idx} />
            </div>
            <button
              type="submit"
              class="btn btn-sm no-animation btn-active square {loadings[idx]
                ? 'loading'
                : ''} my-2"
              style="text-transform: none;"><div>Update</div></button
            >
          </label>
        </label>
      </form>
    </div>
  {/each}
  <div class="p-5">
    <h1 class="text-3xl font-medium flex flex-row justify-center">
      A2SV Students Board
    </h1>
    <div class="container mx-auto shadow-sm lg:max-w-2xl p-5">
      {#each $Users as user, idx ("BD" + user.id)}
        <div
          class="container w-100 backdrop-blur-md rounded bg-white bg-opacity-5 py-1 my-4"
          transition:fly={{ y: 30 }}
        >
          <div
            class="absolute top-0 right-0"
            style="margin-top: -10px; margin-right:-10px;"
          >
            <div class="avatar placeholder">
              <div
                class="bg-neutral-focus text-neutral-content rounded-full w-6"
              >
                <span class="text-xs">{idx + 1}</span>
              </div>
            </div>
          </div>
          <div class="flex flex-row justify-start p-1 items-start">
            <div class="avatar placeholder">
              <div
                class="bg-neutral-focus text-neutral-content rounded-full w-16"
              >
                <div class="absolute top-0 right-0 text-sm">
                  {idx == 0 ? "🤓" : emojis[idx]}
                </div>
                {#if 0 == idx}
                  <img src="/profile.png" />
                {:else}
                  <span class="text-xl">{user.name[0].toUpperCase()}</span>
                {/if}
              </div>
            </div>
            <div class="flex flex-col h-100 mx-2 justify-between w-full">
              <div class="text-base">{user.name}</div>
              <label for="my-modal-4{user.id}" class="cursor-pointer">
                <div class="badge badge-primary rounded my-1">
                  {idx == 0
                    ? "Built different"
                    : user.title == ""?"Edit placeholder":user.title}
                </div></label
              >
              <div class="mb-1">
                {user.totalCount} Problems Solved

                <div
                  class="dropdown dropdown-right dropdown-end dropdown-hover"
                >
                  <label
                    tabindex="0"
                    class="btn btn-circle btn-ghost btn-xs text-info"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      class="w-4 h-4 stroke-current"
                      ><path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                      /></svg
                    >
                  </label>
                  <div
                    tabindex="0"
                    class="card compact dropdown-content shadow bg-base-100 rounded-box w-96"
                  >
                    <div class="card-body">
                      <div class="flex flex-row justify-evenly items-start">
                        <div>
                          <div class="my-1 text-sm">
                            Easy Solved - {user.easyCount} Questions
                          </div>
                          <div class="my-0.5 text-sm">
                            Medium Solved - {user.midCount} Questions
                          </div>
                          <div class="my-0.5 text-sm">
                            Hard Solved - {user.hardCount} Questions
                          </div>
                          <div class="my-0.5 text-xs">
                            Last updated {dayjs(dayjs(user.lastUpdated)).fromNow()}
                          </div>
                        </div>
                        <div
                          class="mx-2 flex flex-col justify-start items-center my-1"
                        >
                          <div class="text-xs mb-1">Acceptance</div>
                          <div
                            class="radial-progress subpixel-antialiased"
                            style="--value:70; --size:3rem;color:{getColorSubmission(
                              user.totalCount,
                              user.totalSubmission
                            )};"
                          >
                            <div class="text-xs">
                              {Math.trunc(
                                (100 * user.totalCount) / user.totalSubmission
                              )}%
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="barContainer mb-2">
                <div class="bar">
                  <div
                    class="green"
                    style="width: {Math.trunc(
                      (100 * user.easyCount) / user.totalCount
                    )}%;"
                  />
                  <div
                    class="yellow"
                    style="width:{Math.trunc(
                      (100 * user.midCount) / user.totalCount
                    )}%;"
                  />
                  <div
                    class="red"
                    style="width: {Math.trunc(
                      (100 * user.hardCount) / user.totalCount
                    )}%;"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      {/each}
    </div>
  </div>
</div>

<style>
</style>
