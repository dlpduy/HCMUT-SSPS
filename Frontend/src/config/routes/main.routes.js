import LayoutPage from './../../layout/index';
import Main from "../../page/public/Main";
import Register from "../../page/public/Register";
import Login from "../../page/public/Login";
import Payment from '../../page/public/payment';

const mainRouter = { 
  path: "",
  element: LayoutPage,
  children: [
    {
      subPath: "", 
      Component: Main ,
    },
    {
      subPath: "main",
      Component: Main,
    },
    {
      subPath: "register",
      Component: Register,
    },
    {
      subPath: "login",
      Component: Login,
    },
    {
      subPath: "payment-result",
      Component: Payment,
    },
  ]
}
  
  
;
export default mainRouter;