import { useState, useEffect, useContext } from "react";
import { flushSync } from "react-dom";
import { Table, Pagination, Button, Modal, Form, Input, Tooltip } from "antd";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import { createNewPrinter, updatePrinter, deletePrinterById } from "../../../../api/spso";
import { MyContext } from "../../../../config/context";
import { getAllPrinters } from "./../../../../api/shared";

const { TextArea } = Input;

const PrinterManagement = () => {
  const [form] = Form.useForm();
  const [formEdit] = Form.useForm();
  const { openNotification } = useContext(MyContext);

  const [printerList, setPrinterList] = useState([]);
  const [page, setPage] = useState(0);
  const [isNeedFetch, setIsNeedFetch] = useState(false);
  const [fieldId, setFieldId] = useState();
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [openAddPrinterModal, setOpenAddPrinterModal] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);

  useEffect(() => {
    getAllPrinters({ page: page }).then((res) => {
      setPrinterList(res.data);
      setIsNeedFetch(true);
    });
  }, [page, isNeedFetch]);

  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      width: "2%",
    },
    {
      title: "Tên thương hiệu",
      dataIndex: "brand",
      width: "10%",
    },
    {
      title: "Mẫu mã",
      dataIndex: "model",
      width: "15%",
    },
    {
      title: "Mô tả ngắn",
      dataIndex: "description",
      width: "35%",
      render: (text) => <span style={{ whiteSpace: "nowrap", textOverflow: "ellipsis", overflow: "hidden" }}>{text}</span>,
    },
    {
      title: "Cơ sở",
      dataIndex: "campusName",
      width: "10%",
    },
    {
      title: "Vị trí",
      dataIndex: "buildingName",
      width: "10%",
    },
    {
      title: "Phòng",
      dataIndex: "roomNum",
      width: "8%",
    },
    {
      title: "Chỉnh sửa",
      dataIndex: null,
      width: "10%",
      render: (values) => (
        <div className="flex flex-row gap-5">
          <Tooltip placement="top" title={"Xóa máy in"}>
            <Button
              onClick={() => {
                flushSync(() => {
                  setFieldId({ id: values.id, model: values.model });
                });
                setOpenDeleteModal(true);
              }}
              icon={<DeleteOutlined />}
              className="border-none"
            />
          </Tooltip>
          <Tooltip placement="top" title={"Sửa thông tin"}>
            <Button
              onClick={() => {
                flushSync(() => {
                  setFieldId({ id: values.id, model: values.model });
                });
                formEdit.setFieldValue("model", values.model);
                formEdit.setFieldValue("brand", values.brand);
                formEdit.setFieldValue("description", values.description);
                formEdit.setFieldValue("campusName", values.campusName);
                formEdit.setFieldValue("buildingName", values.buildingName);
                formEdit.setFieldValue("roomNum", values.roomNum);
                setOpenEditModal(true);
              }}
              icon={<EditOutlined />}
              className="border-none"
            />
          </Tooltip>
        </div>
      ),
    },
  ];

  const handlePageChange = (e) => {
    setPage(e);
  };

  const handleAddPrinter = (values) => {
    createNewPrinter(values).then((res) => {
      if (res.error) {
        openNotification(res.error, "error");
      } else {
        openNotification(res.message, "success");
        setIsNeedFetch(false);
        setOpenAddPrinterModal(false);
      }
    });
  };

  const handleDeletePrinter = (id) => {
    deletePrinterById(id).then((res) => {
      if (res.error) {
        openNotification(res.error, "error");
      } else {
        openNotification(res.message, "success");
        setIsNeedFetch(false);
        setFieldId();
        setOpenDeleteModal(false);
      }
    });
  };

  const handleEditPrinter = (values) => {
    const dataSend = { ...values, id: fieldId.id };
    updatePrinter(dataSend).then((res) => {
      if (res.error) {
        openNotification(res.error, "error");
      } else {
        openNotification(res.message, "success");
        setIsNeedFetch(false);
        setFieldId();
        setOpenEditModal(false);
      }
    });
  };

  return (
    <div className="w-full h-full bg-white pt-5 px-5 flex flex-col gap-5">
      <h2 className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Danh sách máy in</h2>
      <Button onClick={() => setOpenAddPrinterModal(true)} className="w-52 h-12 bg-darkblue font-bold text-white text-2xl">
        Thêm máy in
      </Button>
      <Table columns={columns} dataSource={printerList?.content} pagination={false} />
      <Pagination
        defaultCurrent={1}
        total={printerList?.total || 0}
        current={page + 1}
        pageSize={printerList?.pageSize || 10}
        onChange={handlePageChange}
        className="self-end"
      />

      <Modal
        className="!w-2/3"
        title="Thêm máy in"
        open={openAddPrinterModal}
        centered={true}
        onCancel={() => {
          setOpenAddPrinterModal(false);
        }}
        footer={false}
      >
        <Form layout="horizontal" onFinish={handleAddPrinter} className="w-full" form={form}>
          <div className="grid grid-cols-3 grid-rows-1 gap-5">
            <div className="flex flex-col col-span-2">
              <div className="px-2 pb-1 font-medium text-base">Mẫu mã</div>
              <Form.Item
                name="model"
                rules={[
                  {
                    required: true,
                    message: "Cần phải nhập mẫu mã!",
                  },
                ]}
              >
                <Input className="!w-full h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col col-start-3">
              <div className="px-2 pb-1 font-medium text-base">Tên thương hiệu</div>
              <Form.Item
                name="brand"
                rules={[
                  {
                    required: true,
                    message: "Cần phải thương hiệu!",
                  },
                ]}
              >
                <Input className="!w-full h-10 text-base" />
              </Form.Item>
            </div>
          </div>
          <div className="px-2 pb-1 font-medium text-base">Mô tả</div>
          <Form.Item name="description">
            <TextArea className="!w-full h-10 text-base" />
          </Form.Item>
          <div className="grid grid-cols-3 grid-rows-1 gap-5">
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Cơ sở</div>
              <Form.Item
                name="campusName"
                rules={[
                  {
                    required: true,
                    message: "Cần phải cơ sở!",
                  },
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Vị trí</div>
              <Form.Item
                name="buildingName"
                rules={[
                  {
                    required: true,
                    message: "Cần phải vị trí!",
                  },
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Phòng</div>
              <Form.Item
                name="roomNum"
                rules={[
                  {
                    required: true,
                    message: "Cần phải nhập số phòng!",
                  },
                  ({ setFieldValue }) => ({
                    validator(_, value) {
                      if (!/[0-9]/.test(Number(value))) {
                        setFieldValue("roomNum", value.slice(0, -1));
                        return Promise.resolve();
                      }
                      return Promise.resolve();
                    },
                  }),
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
          </div>
          <Form.Item>
            <Button className="w-52 h-12 bg-darkblue font-bold text-white text-2xl" htmlType="submit">
              Thêm
            </Button>
          </Form.Item>
        </Form>
      </Modal>
      <Modal
        title={`Delete printer ${fieldId?.model}?`}
        open={openDeleteModal}
        centered={true}
        onCancel={() => {
          flushSync(() => setFieldId());
          setOpenDeleteModal(false);
        }}
        onOk={() => {
          handleDeletePrinter(fieldId?.id);
        }}
      />
      {/* edit printer */}
      <Modal
        className="!w-2/3"
        title={`Edit ${fieldId?.model}?`}
        open={openEditModal}
        centered={true}
        onCancel={() => {
          setOpenEditModal(false);
        }}
        footer={false}
      >
        <Form layout="horizontal" onFinish={handleEditPrinter} className="w-full" form={formEdit}>
          <div className="grid grid-cols-3 grid-rows-1 gap-5">
            <div className="flex flex-col col-span-2">
              <div className="px-2 pb-1 font-medium text-base">Mẫu mã</div>
              <Form.Item
                name="model"
                rules={[
                  {
                    required: true,
                    message: "Cần phải mẫu mã!",
                  },
                ]}
              >
                <Input className="!w-full h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col col-start-3">
              <div className="px-2 pb-1 font-medium text-base">Tên thương hiệu</div>
              <Form.Item
                name="brand"
                rules={[
                  {
                    required: true,
                    message: "Cần phải thương hiệu!",
                  },
                ]}
              >
                <Input className="!w-full h-10 text-base" />
              </Form.Item>
            </div>
          </div>
          <div className="px-2 pb-1 font-medium text-base">Mô tả</div>
          <Form.Item name="description">
            <TextArea className="!w-full h-10 text-base" />
          </Form.Item>
          <div className="grid grid-cols-3 grid-rows-1 gap-5">
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Cơ sở</div>
              <Form.Item
                name="campusName"
                rules={[
                  {
                    required: true,
                    message: "Cần phải cơ sở!",
                  },
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Vị trí</div>
              <Form.Item
                name="buildingName"
                rules={[
                  {
                    required: true,
                    message: "Cần phải vị trí!",
                  },
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
            <div className="flex flex-col">
              <div className="px-2 pb-1 font-medium text-base">Phòng</div>
              <Form.Item
                name="roomNum"
                rules={[
                  {
                    required: true,
                    message: "Cần phải nhập số phòng!",
                  },
                  ({ setFieldValue }) => ({
                    validator(_, value) {
                      if (!/[0-9]/.test(Number(value))) {
                        setFieldValue("roomNum", value.slice(0, -1));
                        return Promise.resolve();
                      }
                      return Promise.resolve();
                    },
                  }),
                ]}
              >
                <Input className="h-10 text-base" />
              </Form.Item>
            </div>
          </div>
          <Form.Item>
            <Button className="w-52 h-12 bg-darkblue font-bold text-white text-2xl" htmlType="submit">
              Chỉnh sửa
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default PrinterManagement;
