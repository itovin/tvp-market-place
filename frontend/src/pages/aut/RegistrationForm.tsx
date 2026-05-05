import { useState } from "react";
import tvp from "../../assets/tvp.png";
import Textbox from "../../components/Textbox";
import { registerUser } from "../../api/auth";
import { useNavigate } from "react-router-dom";

type User = {
  name: string;
  email?: string;
  username: string;
  password: string;
};
export default function RegistrationForm() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState<{
    username?: string;
    email?: string;
    confirmPassword?: string;
  }>({});
  const navigate = useNavigate();

  function handleChange(setter: React.Dispatch<React.SetStateAction<string>>) {
    return (input: string) => setter(input);
  }

  function handleSubmit(e: React.SubmitEvent) {
    e.preventDefault();
    const newError: typeof errors = {};
    if (password !== confirmPassword) {
      newError.confirmPassword = "Passwords do not match";
      setErrors(newError);
      return;
    }
    setErrors(newError);
    handleRegister();
  }

  function handleRegister() {
    console.log("SENDING REGISTER REQUEST");
    const newUser: User = {
      name: name,
      email: email || undefined,
      username: username,
      password: password,
    };

    registerUser(newUser)
      .then((res) => {
        console.log(res.data);
        navigate("/login");
      })
      .catch((error) => {
        const newError: typeof errors = {};
        const response = error.response;
        console.log(response?.data);
        const status = response?.status;
        const field = response?.data?.field;
        const message = response?.data?.message;
        if (status === 409) {
          if (field === "email") {
            newError.email = message;
          } else {
            newError.username = message;
          }
        }
        setErrors(newError);
      });

  }

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
        >
          <img className="w-30 h-30" src={tvp} alt="logo" />
        </a>
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Create an account
            </h1>
            <form
              className="space-y-4 md:space-y-6"
              autoComplete="off"
              onSubmit={handleSubmit}
            >
              <Textbox
                htmlFor="name"
                label="Name"
                value={name}
                name="name"
                id="name"
                type="text"
                placeHolder="Your Name"
                min={2}
                max={30}
                pattern="^[a-zA-Z ]+$"
                isRequired={true}
                handleOnChange={handleChange(setName)}
              />

              <Textbox
                htmlFor="email"
                label="Email"
                value={email}
                name="email"
                id="email"
                type="email"
                placeHolder="email@example.com"
                isRequired={false}
                error={errors.email}
                handleOnChange={handleChange(setEmail)}
              />

              <Textbox
                htmlFor="username"
                label="Username"
                value={username}
                name="username"
                id="username"
                type="text"
                placeHolder="username"
                min={4}
                max={30}
                pattern="^[a-zA-Z0-9_]*$"
                isRequired={true}
                error={errors.username}
                handleOnChange={handleChange(setUsername)}
              />

              <Textbox
                htmlFor="password"
                label="Password"
                value={password}
                name="password"
                id="password"
                type="password"
                placeHolder="••••••••"
                min={8}
                max={30}
                isRequired={true}
                handleOnChange={handleChange(setPassword)}
              />
              <Textbox
                htmlFor="confirmPassword"
                label="Confirm Password"
                name="confirmPassword"
                id="confirmPassword"
                type="password"
                placeHolder="••••••••"
                min={8}
                max={30}
                isRequired={true}
                error={errors.confirmPassword}
                handleOnChange={handleChange(setConfirmPassword)}
              />
              {/* <div className="flex items-start">
                <div className="flex items-center h-5">
                  <input
                    id="terms"
                    aria-describedby="terms"
                    type="checkbox"
                    className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                    required
                  />
                </div>
                <div className="ml-3 text-sm">
                  <label
                    htmlFor="terms"
                    className="font-light text-gray-500 dark:text-gray-300"
                  >
                    I accept the{" "}
                    <a
                      className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                      href="#"
                    >
                      Terms and Conditions
                    </a>
                  </label>
                </div>
              </div> */}
              <button
                type="submit"
                className="
                w-full cursor-pointer
                text-white 
                bg-primary-600 
                hover:bg-primary-700 
                focus:ring-4 
                focus:outline-none 
                focus:ring-primary-300 
                font-medium 
                rounded-lg 
                text-sm px-5 
                py-2.5 
                text-center 
                dark:bg-primary-600 
                dark:hover:bg-primary-700 
                dark:focus:ring-primary-800"
              >
                Create an account
              </button>
              <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                Already have an account?{" "}
                <a
                  href="/login"
                  className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                >
                  Login here
                </a>
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}
