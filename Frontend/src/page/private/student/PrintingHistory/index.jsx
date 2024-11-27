import { useState } from "react";
import { SearchOutlined } from "@ant-design/icons";
import { Input, Table } from "antd";

const { Search } = Input;

const PrintingHistory = () => {
  const [setSearchText] = useState("");

  const dataSource = [
    {
      key: "1",
      studentName: "Nguyễn Văn A",
      studentId: "0000001",
      documentName: "Tài liệu ôn thi cuối kỳ",
      printerId: "ABCXYZ123",
      date: "27/10/2024",
      quantity: 2,
      cost: "30,000",
    },
    {
      key: "2",
      studentName: "Nguyễn Văn A",
      studentId: "0000001",
      documentName: "Tài liệu ôn thi cuối kỳ",
      printerId: "ABCXYZ123",
      date: "27/10/2024",
      quantity: 2,
      cost: "30,000",
    },
  ];

  const [tableData, setTableData] = useState(dataSource);

  const columns = [
    {
      title: "Tên sinh viên",
      dataIndex: "studentName",
      key: "studentName",
    },
    {
      title: "Mã số sinh viên",
      dataIndex: "studentId",
      key: "studentId",
    },
    {
      title: "Tên tài liệu",
      dataIndex: "documentName",
      key: "documentName",
    },
    {
      title: "Mã máy in",
      dataIndex: "printerId",
      key: "printerId",
    },
    {
      title: "Ngày in",
      dataIndex: "date",
      key: "date",
    },
    {
      title: "Số lượng đã in",
      dataIndex: "quantity",
      key: "quantity",
    },
    {
      title: "Giá tiền",
      dataIndex: "cost",
      key: "cost",
    },
  ];

  const onSearch = (value) => {
    const filtered = dataSource.filter((item) => Object.values(item).some((field) => String(field).toLowerCase().includes(value.toLowerCase())));
    setTableData(filtered);
  };

  return (
    <div className="w-full h-full bg-white pt-5 px-5">
      <h2 className="w-1/2 border-b border-slate-400 mb-5 pb-3 text-2xl font-bold text-darkblue">Lịch sử sử dụng dịch vụ</h2>
      <Search
        size="large"
        placeholder="Search..."
        prefix={<SearchOutlined style={{ color: "#1890ff" }} />}
        style={{ width: 400, borderRadius: "8px" }}
        onSearch={onSearch}
        onChange={(e) => setSearchText(e.target.value)}
      />
      <Table dataSource={tableData} columns={columns} />
    </div>
  );
};

export default PrintingHistory;
