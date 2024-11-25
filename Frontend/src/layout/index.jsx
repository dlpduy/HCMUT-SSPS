import { useState, useEffect } from "react";
import { Outlet } from "react-router-dom";
import { Layout, notification } from "antd";
import Cookies from "js-cookie";
import HeaderPage from "./Header";
import { MyContext } from "../config/context";
import "react-toastify/dist/ReactToastify.css";

const { Header, Content } = Layout;

const LayoutPage = () => {
  const [role, setRole] = useState(Cookies.get("role") ? Cookies.get("role")?.toLowerCase() : "");
  useEffect(() => {}, [role]);

  const [api, contextHolder] = notification.useNotification();
  const openNotification = (placement, type) => {
    api[type]({
      message: `${type.toUpperCase()}`,
      description: placement,
    });
  };

  console.log(role);
  return (
    <MyContext.Provider value={{ setRole, openNotification }}>
      <Layout className="min-h-screen w-full h-screen">
        <Header className="h-16 w-full bg-[#E2E2E2]">
          <HeaderPage role={role} />
        </Header>
        <Content className="h-full w-full">
          <Outlet />
        </Content>
      </Layout>
      {contextHolder}
    </MyContext.Provider>
  );
};

export default LayoutPage;
