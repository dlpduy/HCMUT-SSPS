import { useState, useEffect } from "react";
import { Table, Pagination } from "antd";
import { getAllPrintingHistory } from "../../../../api/spso";

const StudentPrintingHistory = () => {
  const [printHistList, setPrintHistList] = useState([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    getAllPrintingHistory({ page: page }).then((res) => setPrintHistList(res.data));
  }, [page]);

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

  const handlePageChange = (e) => {
    setPage(e);
  };
  return (
    <div className="w-full h-full bg-white pt-5 px-5 flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Lịch sử sử dụng dịch vụ</h2>
      <Table dataSource={printHistList?.content} columns={columns} pagination={false} />
      <Pagination
        defaultCurrent={1}
        total={printHistList?.total || 0}
        current={page + 1}
        pageSize={printHistList?.pageSize || 10}
        onChange={handlePageChange}
        className="self-end"
      />
    </div>
  );
};

export default StudentPrintingHistory;
