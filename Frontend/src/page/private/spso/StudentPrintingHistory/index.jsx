import { useState } from 'react';
import { SearchOutlined } from '@ant-design/icons';
import { Input, Table, Button, Flex } from 'antd';

const { Search } = Input;

const StudentPrintingHistory = () => {
  const [setSearchText] = useState('');
  const [selectedRow, setSelectedRow] = useState(null);
  const [viewDetails, setViewDetails] = useState(false);

  const dataSource = [
    {
      key: '1',
      studentName: 'Nguyễn Văn A',
      studentId: '0000001',
      documentName: 'Tài liệu ôn thi cuối kỳ',
      printerId: 'ABCXYZ123',
      date: '27/10/2024',
      quantity: 2,
      cost: '30,000',
    },
  ];

  const detailData = {
  '1': {
    studentName: 'Nguyễn Văn A',
    studentId: '0000001',
    printerName: 'Canon',
    function: 'In màu',
    place: 'H1',
    documentSize: '5 Mb',
    page: 10,
  },
  };

  const [tableData, setTableData] = useState(dataSource);

  const columns = [
    {
      title: 'Tên sinh viên',
      dataIndex: 'studentName',
      key: 'studentName',
    },
    {
      title: 'Mã số sinh viên',
      dataIndex: 'studentId',
      key: 'studentId',
    },
    {
      title: 'Tên tài liệu',
      dataIndex: 'documentName',
      key: 'documentName',
    },
    {
      title: 'Mã máy in',
      dataIndex: 'printerId',
      key: 'printerId',
    },
    {
      title: 'Ngày in',
      dataIndex: 'date',
      key: 'date',
    },
    {
      title: 'Số lượng đã in',
      dataIndex: 'quantity',
      key: 'quantity',
    },
    {
      title: 'Giá tiền',
      dataIndex: 'cost',
      key: 'cost',
    },
  ];

  const onSearch = (value) => {
    const filtered = dataSource.filter((item) =>
      Object.values(item).some((field) =>
        String(field).toLowerCase().includes(value.toLowerCase())
      )
    );
    setTableData(filtered);
  };

  const detailColumns = [
    { title: 'Trường', dataIndex: 'field', key: 'field', width: '30%' },
    { title: 'Giá trị', dataIndex: 'value', key: 'value', width: '70%' },
  ];

  // Khi click vào hàng
  const onRowClick = (record) => {
    setSelectedRow(record);
    setViewDetails(true); // Hiển thị chi tiết
  };

  const selectedDetailData = selectedRow
    ? [
        { key: '1', field: 'Tên sinh viên', value: detailData[selectedRow.key].studentName },
        { key: '2', field: 'Mã số sinh viên', value: detailData[selectedRow.key].studentId },
        { key: '3', field: 'Ngày in', value: selectedRow.date },
        { key: '4', field: 'Mã máy in', value: selectedRow.printerId },
        { key: '5', field: 'Tên máy in', value: detailData[selectedRow.key].printerName },
        { key: '6', field: 'Chức năng', value: detailData[selectedRow.key].function },
        { key: '7', field: 'Địa điểm', value: detailData[selectedRow.key].place },
        { key: '8', field: 'Tên tài liệu', value: selectedRow.documentName },
        { key: '9', field: 'Kích thước', value: detailData[selectedRow.key].documentSize },
        { key: '10', field: 'Số trang', value: detailData[selectedRow.key].page },
        { key: '11', field: 'Tổng tiền', value: selectedRow.cost },
      ]
    : [];

  return (
    <div style={{ padding: '20px' }}>
      {!viewDetails ? (
        <>
          {/* Thanh tìm kiếm */}
          <Search
            size="large"
            placeholder="Search..."
            prefix={<SearchOutlined style={{ color: '#1890ff' }} />}
            style={{ width: 400, borderRadius: '8px' }}
            onSearch={onSearch}
            onChange={(e) => setSearchText(e.target.value)}
          />
          <Table
            dataSource={tableData}
            columns={columns}
            onRow={(record) => ({
              onClick: () => onRowClick(record),
              onMouseEnter: (e) => (e.currentTarget.style.backgroundColor = '#1890ff'),
              onMouseLeave: (e) => (e.currentTarget.style.backgroundColor = ''),
            })}
            rowClassName={(record) =>
              selectedRow && selectedRow.key === record.key ? 'ant-table-row-selected' : ''
            }
          />
        </>
      ) : (
        <div>
          <Table
            dataSource={selectedDetailData}
            columns={detailColumns}
            pagination={false}
            bordered
            style={{ marginBottom: '20px' }}
          />
          <Flex gap="middle" wrap>
            <Button
            type="primary"
            onClick={() => console.log('Dùng thiết lập này')}
          >
            DÙNG THIẾT LẬP NÀY
          </Button>
          <Button
            type="primary"
            onClick={() => console.log('In tài liệu')}
          >
            IN TÀI LIỆU
          </Button>
          <Button color="primary" variant="outlined" onClick={() => setViewDetails(false)}>
            HỦY
          </Button>
          </Flex>
        </div>
      )}
    </div>
  );
};

export default StudentPrintingHistory;
