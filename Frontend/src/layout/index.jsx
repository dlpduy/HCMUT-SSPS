import { useState, useEffect } from "react";
import { Layout, notification } from "antd";
import Cookies from "js-cookie";
import HeaderPage from "./Header";
import ContentPage from "./Content";
import { MyContext } from "../config/context";

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
  return (
    <MyContext.Provider value={{ setRole, openNotification, role }}>
      <Layout className="min-h-screen w-full h-screen">
        <Header className="h-16 w-full bg-[#E2E2E2]">
          <HeaderPage role={role} />
        </Header>
        <Content className="h-full w-full">
          <ContentPage role={role} />
        </Content>
      </Layout>
      {contextHolder}
    </MyContext.Provider>
  );
};

export default LayoutPage;
