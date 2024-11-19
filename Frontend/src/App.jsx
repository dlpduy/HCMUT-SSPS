import { Route, BrowserRouter, Routes } from "react-router-dom";
import { publicRouter, privateRouter } from "./config/routes";
import Cookies from "js-cookie";
import "./App.css";

const token = Cookies.get("token");
const role = Cookies.get("role");
function App() {
  return (
    <BrowserRouter
      future={{
        v7_startTransition: true,
        v7_relativeSplatPath: true,
      }}
    >
      <Routes>
        {token != undefined &&
          privateRouter.map((routes) => {
            return (
              routes.path == role && (
                <Route path={routes.path} element={<routes.element />}>
                  {routes.children.map(({ subPath, Component }, index) => {
                    return <Route key={index} path={subPath} element={<Component />} />;
                  })}
                </Route>
              )
            );
          })}
        {publicRouter.map((router, index) => {
          return (
            <Route path={router.path} key={index} element={<router.element />}>
              {router.children.map((children, index) => {
                return <Route path={children.path} element={<children.Component />} key={index} />;
              })}
            </Route>
          );
        })}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
