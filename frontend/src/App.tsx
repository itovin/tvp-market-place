import { Routes, Route } from "react-router-dom";
import RegistrationForm from "./pages/aut/RegistrationForm";
import Navbar from "./layout/Navbar";
import Layout from "./layout/Layout";
import Login from "./pages/aut/Login";

export default function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route element={<Layout />} />
        <Route path="/register" element={<RegistrationForm />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </>
  );
}
