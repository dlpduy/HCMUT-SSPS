import { useState, useEffect } from "react";
import { getAllPaper } from "../../../../../api/shared";
import { Select, Input } from "antd";
const { Option } = Select;

const PrinterSetting = ({ setDataSend }) => {
  const [paperTypeList, setPaperTypeList] = useState([]);
  const [error, setError] = useState(true);
  useEffect(() => {
    getAllPaper().then((res) => {
      setPaperTypeList(res.data.map((value) => value.type));
    });
  }, []);
  return (
    <div className="w-full h-full flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Thiết lập bản in</h2>
      <div className="flex flex-col gap-5 w-1/2 self-center">
        <div className="text-base">Số bản in</div>
        <Input placeholder="Nhập số bản in" defaultValue={1} onChange={(e) => setDataSend((prev) => ({ ...prev, numCopy: e.target.value }))} />
        <div className="text-base">Cấu hình in</div>
        <Select
          defaultValue="Single"
          options={[
            { value: "Single", label: "Single" },
            { value: "Double", label: "Double" },
          ]}
          onChange={(value) => {
            setDataSend((prev) => ({ ...prev, sided: value }));
          }}
        />
        <div className="text-base">Loại giấy</div>
        <Select defaultValue={paperTypeList[0]} onChange={(value) => setDataSend((prev) => ({ ...prev, paperType: value }))}>
          {paperTypeList.map((type, index) => (
            <Option value={type} key={index}>
              {type}
            </Option>
          ))}
        </Select>
        <div className="text-base">Chọn mục in</div>
        <Input
          placeholder="Nhập mục in"
          defaultValue={1}
          onChange={(e) => {
            if (/^\d+$|^\d+-\d+$|^\d+(,\d+)*$/.test(String(e.target.value))) {
              setDataSend((prev) => ({ ...prev, printingPages: String(e.target.value) }));
              setError(false);
            } else {
              setError(true);
            }
          }}
        />
        {error && <div className="text-xs text-red-600">Thông tin nhập vào phải là dạng Number hoặc Number-Number hoặc Number,Number,Number... !</div>}
      </div>
    </div>
  );
};

export default PrinterSetting;
