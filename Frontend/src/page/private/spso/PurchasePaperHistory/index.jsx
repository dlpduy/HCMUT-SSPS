import { useState, useEffect } from "react";
import { Table, Pagination } from "antd";
import { getAllPagePurchaseHistory } from "../../../../api/spso";
const PurchasePaperHistory = () => {
  // Định nghĩa cột cho bảng

  const [purchaseHistList, setPurchaseHistList] = useState([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    getAllPagePurchaseHistory({ page: page }).then((res) => setPurchaseHistList(res.data));
  }, [page]);

  const columns = [
    {
      title: "Mã số sinh viên",
      dataIndex: "studentId",
      width: "10%",
    },
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

  const handlePageChange = (e) => {
    setPage(e);
  };

  return (
    <div className="w-full h-full bg-white pt-5 px-5 flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Lịch sử mua giấy in</h2>
      <Table columns={columns} dataSource={purchaseHistList.paymentLogsResponseList} pagination={false} />
      <Pagination
        defaultCurrent={1}
        total={purchaseHistList?.total || 0}
        current={page + 1}
        pageSize={purchaseHistList?.pageSize || 10}
        onChange={handlePageChange}
        className="self-end"
      />
    </div>
  );
};

export default PurchasePaperHistory;
