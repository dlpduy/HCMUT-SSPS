import { Outlet } from "react-router-dom";
import { Layout } from "antd";
import HeaderPage from "./Header";

const { Header, Content } = Layout;

const LayoutPage = () => {
  return (
    <Layout className="w-screen h-screen">
      <Header className="h-16 w-full">
        <HeaderPage />
      </Header>
      <Content className="h-full w-full">
        <Outlet />
      </Content>
    </Layout>
  );
};

export default LayoutPage;
