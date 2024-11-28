import { useEffect } from "react";
import { Outlet } from "react-router-dom";

const ContentPage = (role) => {
  useEffect(() => {}, [role]);
  return role && <Outlet />;
};

export default ContentPage;
