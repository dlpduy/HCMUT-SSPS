import { Table } from "antd";

const PurchasePaperHistory = () => {
  // Định nghĩa cột cho bảng
  const columns = [
    {
      title: "Ngày giao dịch",
      dataIndex: "paymentDateTime",
      render: (value) => {
        const date = new Date(value);
        return `${String(date.getHours())}:${String(date.getMinutes())} ${String(date.getDate())} - ${String(date.getMonth() + 1)} - ${date.getFullYear()}`;
      },
      width: "15%",
    },
    {
      title: "Mã giao dịch",
      dataIndex: "paymentLogsId",
      width: "10%",
    },
    {
      title: "Loại giấy",
      dataIndex: "paperType",
      width: "10%",
    },
    {
      title: "Đơn giá",
      dataIndex: "unitPrice",
      width: "10%",
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
      width: "10%",
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalPrice",
      width: "10%",
    },
  ];

  // Dữ liệu giả
  const data = [
    {
      paymentDateTime: "2024-11-27T14:35:00Z", // ISO date format
      paymentLogsId: "TXN001",
      paperType: "A4",
      unitPrice: 2000, // VND
      quantity: 10,
      totalPrice: 20000,
    },
    {
      paymentDateTime: "2024-11-27T16:15:00Z",
      paymentLogsId: "TXN002",
      paperType: "A3",
      unitPrice: 3000,
      quantity: 5,
      totalPrice: 15000,
    },
    {
      paymentDateTime: "2024-11-26T09:20:00Z",
      paymentLogsId: "TXN003",
      paperType: "A5",
      unitPrice: 1000,
      quantity: 20,
      totalPrice: 20000,
    },
    {
      paymentDateTime: "2024-11-26T11:00:00Z",
      paymentLogsId: "TXN004",
      paperType: "A4",
      unitPrice: 2500,
      quantity: 8,
      totalPrice: 20000,
    },
    {
      paymentDateTime: "2024-11-25T15:45:00Z",
      paymentLogsId: "TXN005",
      paperType: "Giấy màu",
      unitPrice: 5000,
      quantity: 4,
      totalPrice: 20000,
    },
  ];

  return (
    <div className="w-full h-full bg-white pt-5 px-5 flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Lịch sử mua giấy in</h2>
      <Table columns={columns} dataSource={data} pagination={false} />
    </div>
  );
};

export default PurchasePaperHistory;
