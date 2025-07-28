import { createContext, useContext, useState } from "react";

const API_BASE_URL = "http://localhost:5000/api";
const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const userData = localStorage.getItem("userInfo");

  const [user, setUser] = useState(() => {
    try {
      return userData ? JSON.parse(userData) : null;
    } catch (error) {
      console.error("Failed to parse userData:", error);
      localStorage.removeItem("userInfo");
      return null;
    }
  });

  const [isLoading, setIsLoading] = useState(false);

  const signin = async (email, password) => {
    setIsLoading(true);
    try {
      const res = await fetch(`${API_BASE_URL}/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      const data = await res.json();
      if (res.ok) {
        localStorage.setItem("userInfo", JSON.stringify(data));
        setUser(data);
        return { success: true };
      } else {
        return { success: false, error: data.message };
      }
    } catch (err) {
      return { success: false, error: "Login failed. Try again." };
    } finally {
      setIsLoading(false);
    }
  };

  const signup = async (name, email, password) => {
    setIsLoading(true);
    try {
      const res = await fetch(`${API_BASE_URL}/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }),
      });

      const data = await res.json();
      if (res.ok) {
        localStorage.setItem("userInfo", JSON.stringify(data));
        setUser(data);
        return { success: true };
      } else {
        return { success: false, error: data.message };
      }
    } catch (err) {
      return { success: false, error: "Signup failed. Try again." };
    } finally {
      setIsLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("userInfo");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, isLoading, signin, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
