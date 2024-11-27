import LayoutPage from './../../layout/index';
import Dashboard from '../../page/private/spso/Dashboard';
import ConfigSystem from '../../page/private/spso/ConfigSystem';
import PrinterManagement from './../../page/private/spso/PrinterManagement/index';
import StudentPrintingHistory from '../../page/private/spso/StudentPrintingHistory';
import PurchasePaperHistory from './../../page/private/spso/PurchasePaperHistory/index';

const spsoRouter = { 
    path: "spso",
    element: LayoutPage,
    children: [
      {
        subPath: "dashboard",
        Component: Dashboard,
      },
      {
        subPath: "config-system",
        Component: ConfigSystem,
      },
      {
        subPath: "printer-management",
        Component: PrinterManagement,
      },
      {
        subPath: "printing-history",
        Component: StudentPrintingHistory,
      },
      {
        subPath: "purchase-paper-history",
        Component: PurchasePaperHistory,
      },
      {
        subPath: "", 
        Component: Dashboard,
      },
    ]}
  
  
;
export default spsoRouter;