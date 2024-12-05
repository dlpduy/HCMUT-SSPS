import { useState, useEffect } from "react";
import { SearchOutlined } from "@ant-design/icons";
import { getPrintHistory } from "../../../../api/student";
import { Input, Table } from "antd";

const { Search } = Input;

const PrintingHistory = () => {
  const [printHistoryList, setPrintHistoryList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getPrintHistory().then((res) => {
      setPrintHistoryList(res.data);
      setIsLoading(true);
    });
  }, []);

  const [setSearchText] = useState("");

  const columns = [
    {
      title: "Mã máy in",
      dataIndex: "printerId",
      width: "10%",
    },
    {
      title: "Tên tài liệu",
      dataIndex: null,
      render: (value) => value.fileName + value.fileType,
      width: "20%",
    },
    {
      title: "Trang được in",
      dataIndex: "printingPages",
      width: "10%",
    },
    {
      title: "Loại giấy in",
      dataIndex: "paperType",
      width: "10%",
    },
    {
      title: "Ngày in",
      dataIndex: "time",
      render: (value) => {
        const date = new Date(value);
        return `${String(date.getHours())}:${String(date.getMinutes())} ${String(date.getDate())} - ${String(date.getMonth() + 1)} - ${date.getFullYear()}`;
      },
      width: "20%",
    },
    {
      title: "Số lượng bản in",
      dataIndex: "numCopy",
      width: "10%",
    },
    {
      title: "Cấu hình in",
      dataIndex: "sided",
      width: "10%",
    },
    {
      title: "Số lượng trang tài liệu",
      dataIndex: "numPages",
      width: "10%",
    },
  ];

  return (
    <div className="w-full h-full bg-white pt-5 px-5">
      <h2 className="w-1/2 border-b border-slate-400 mb-5 pb-3 text-2xl font-bold text-darkblue">Lịch sử sử dụng dịch vụ</h2>
      <Search
        size="large"
        placeholder="Search..."
        prefix={<SearchOutlined style={{ color: "#1890ff" }} />}
        style={{ width: 400, borderRadius: "8px" }}
        // onSearch={onSearch}
        onChange={(e) => setSearchText(e.target.value)}
      />
      <Table dataSource={printHistoryList} columns={columns} loading={!isLoading} />
    </div>
  );
};

export default PrintingHistory;
