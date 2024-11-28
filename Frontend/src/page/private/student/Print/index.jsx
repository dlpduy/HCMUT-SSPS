import ChooseDocument from "./ChooseDocument";
import PrinterSetting from "./PrinterSetting";
import ChoosePrinter from "./ChoosePrinter";

const Print = () => {
  return (
    <div>
      <ChooseDocument></ChooseDocument>
      <PrinterSetting></PrinterSetting>
      <ChoosePrinter></ChoosePrinter>
    </div>
  );
};

export default Print;
