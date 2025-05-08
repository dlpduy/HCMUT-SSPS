import LayoutPage from './../../layout/index';
import PurchasePaper from '../../page/private/student/PurchasePaper/index';
import PurchasePaperHistory from './../../page/private/student/PurchasePaperHistory/index';
import Payment from './../../page/private/student/payment/index';
import Print from './../../page/private/student/Print/index';
import PrintingHistory from './../../page/private/student/PrintingHistory/index';

const studentRouter ={ 
    path: "student",
    element: LayoutPage,
    children: [
      {
        subPath: "purchase-paper",
        Component: PurchasePaper,
      },
      {
        subPath: "purchase-paper-history",
        Component: PurchasePaperHistory,
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
        subPath: "printing-history",
        Component: PrintingHistory,
      },
      {
        subPath: "", 
        Component: PurchasePaper,
      },
    ]}
  
  
;
export default studentRouter;