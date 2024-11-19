import LayoutPage from './../../layout/index';
import BuyPaper from './../../page/private/student/BuyPaper/index';
import BuyPaperHistory from './../../page/private/student/BuyPaperHistory/index';
import Payment from './../../page/private/student/Payment/index';
import Print from './../../page/private/student/Print/index';
import PrintingHistory from './../../page/private/student/PrintingHistory/index';

const studentRouter ={ 
    path: "student",
    element: LayoutPage,
    children: [
      {
        subPath: "buy-paper",
        Component: BuyPaper,
      },
      {
        subPath: "buy-paper-history",
        Component: BuyPaperHistory,
      },
      {
        subPath: "payment",
        Component: Payment,
      },
      {
        subPath: "print",
        Component: Print,
      },
      {
        subPath: "print-history",
        Component: PrintingHistory,
      },
      {
        subPath: "*", 
        Component: BuyPaper,
      },
    ]}
  
  
;
export default studentRouter;