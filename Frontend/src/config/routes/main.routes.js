import Signin from "../../page/public";

const mainRouter = { 
    path: "",
    element: null,
    children: [
      {
        subPath: "main",
        Component: Signin,
      },
      {
        subPath: "*", 
        Component: Signin,
      },
    ]}
  
  
;
export default mainRouter;