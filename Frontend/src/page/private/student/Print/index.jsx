import { useState, useContext } from "react";
import { Button, Modal } from "antd";
import ChooseDocument from "./ChooseDocument";
import PrinterSetting from "./PrinterSetting";
import ChoosePrinter from "./ChoosePrinter";
import { createPrintService } from "../../../../api/student";
import { MyContext } from "../../../../config/context";

const Print = () => {
  const { openNotification } = useContext(MyContext);

  const [index, setIndex] = useState(0);
  const [open, setOpen] = useState(false);

  const [dataSend, setDataSend] = useState({
    fileId: "Chưa được chọn",
    numCopy: 1,
    sided: "Single",
    paperType: "A4",
    printingPages: "1",
    printerId: "Chưa được chọn",
  });
  const handleCreatePrint = () => {
    createPrintService(dataSend).then((res) => {
      if (res.error) openNotification(res.error, "error");
      else {
        openNotification("Yêu cầu in của bạn đã được gửi!", " success");
        setOpen(false);
      }
    });
  };
  const component = [
    <ChooseDocument setDataSend={setDataSend} setIndex={setIndex} index={index} key={0} />,
    <PrinterSetting setDataSend={setDataSend} key={1} />,
    <ChoosePrinter setDataSend={setDataSend} setOpen={setOpen} key={2} />,
  ];
  return (
    <div className="w-full h-full flex flex-col bg-white p-5">
      {component[index]}
      <div className="flex flex-row self-end gap-5">
        <Button onClick={() => setIndex(index - 1)} disabled={index == 0 ? true : false}>
          Quay lại
        </Button>
        <Button onClick={() => setIndex(index + 1)} disabled={index == 2 ? true : false}>
          Tiếp tục
        </Button>
      </div>
      <Modal open={open} onOk={handleCreatePrint} onCancel={() => setOpen(false)} title="Bạn muốn in với cấu hình này?">
        <div>Mã tài liệu: {dataSend.fileId}</div>
        <div>Số bản in: {dataSend.numCopy}</div>
        <div>Cấu hình in: {dataSend.sided}</div>
        <div>Loại giấy: {dataSend.paperType}</div>
        <div>Mục cần in: {dataSend.printingPages}</div>
        <div>Mã máy in: {dataSend.printerId}</div>
      </Modal>
    </div>
  );
};

export default Print;
