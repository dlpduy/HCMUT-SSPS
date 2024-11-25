/* eslint-disable react/prop-types */
import { Link, useNavigate } from "react-router-dom";
import { Button, Tooltip } from "antd";
import logo from "../../assets/img/logo.png";
import Cookies from "js-cookie";
import { LogoutOutlined } from "@ant-design/icons";

const HeaderItems = (role) => {
  if (role?.role == "student")
    return (
      <div className="self-center mx-auto grid grid-cols-4 grid-rows-1 gap-4">
        <Link className="text-[#0022FF] text-base font-bold" to="/main">
          Trang chủ
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/student/print">
          In tài liệu
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/student/purchase-paper">
          Mua giấy in
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/student/print-history">
          Xem lịch sử
        </Link>
      </div>
    );
  if (role?.role == "spso")
    return (
      <div className="self-center mx-auto grid grid-cols-6 grid-rows-1 gap-4">
        <Link className="text-[#0022FF] text-base font-bold" to="/main">
          Trang chủ
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/spso/dashboard">
          Thống kê
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/spso/config-system">
          Thiết lập hệ thống
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/spso/printer-management">
          Quản lý máy in
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/spso/printing-history">
          Lịch sử in
        </Link>
        <Link className="text-[#0022FF] text-base font-bold" to="/spso/purchase-paper-history">
          Lịch sử mua giấy in
        </Link>
      </div>
    );
  return (
    <div className="self-center mx-auto grid grid-cols-2 grid-rows-1 gap-4">
      <Link className="text-[#0022FF] text-base font-bold" to="/main">
        Trang chủ
      </Link>
      <Link className="text-[#0022FF] text-base font-bold" to="/main">
        Liên hệ
      </Link>
    </div>
  );
};

const HeaderPage = ({ role }) => {
  const navigate = useNavigate();
  return (
    <div className="w-full h-full flex flex-row items-center">
      <img className="h-14 cursor-pointer" src={logo} alt="" onClick={() => navigate("/main", { replace: true })} />
      <HeaderItems role={role} />
      {role ? (
        <div className="flex flex-row w-fit">
          <div className="w-20">{role}</div>
          <Tooltip placement="bottomLeft" title={"Log out!"}>
            <Button
              icon={<LogoutOutlined />}
              className="bg-transparent border-none hover:!bg-transparent hover:!border-none self-center"
              onClick={() => {
                Cookies.remove("role");
                Cookies.remove("token");
                Cookies.remove("name");
              }}
            />
          </Tooltip>
        </div>
      ) : (
        <Button className="h-12 w-32 text-white bg-[#2600FF] text-lg font-bold" onClick={() => navigate("/login", { replace: true })}>
          Đăng nhập
        </Button>
      )}
    </div>
  );
};

export default HeaderPage;
