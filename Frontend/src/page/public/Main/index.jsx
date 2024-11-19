import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import main from "../../../assets/img/main.png";
import student from "../../../assets/img/student.png";
import spso from "../../../assets/img/spso.png";

const role = Cookies.get("role") || "";
const Main = () => {
  const navigate = useNavigate();
  return role == "student" ? (
    <div className="w-full h-full grid grid-cols-2 grid-rows-1 gap-4 p-5 ">
      <div className="w-full flex items-center flex-col justify-center gap-5">
        <div className="w-full text-3xl tracking-wider font-bold text-center">Trang dành cho sinh viên </div>
        <div className="w-full text-3xl tracking-wider font-bold text-center">Trường Đại học Bách khoa</div>
        <div className="w-full text-3xl tracking-wider font-bold text-center">ĐHQG HỒ CHÍ MINH</div>
      </div>
      <img src={student} alt="" className="place-self-center" />
    </div>
  ) : role == "spso" ? (
    <div className="w-full h-full grid grid-cols-2 grid-rows-1 gap-4 p-5">
      <div className="w-full flex items-center flex-col justify-center gap-5">
        <div className="w-full text-3xl tracking-wider font-bold text-center">Trang dành cho SPSO </div>
        <div className="w-full text-3xl tracking-wider font-bold text-center">Trường Đại học Bách khoa</div>
        <div className="w-full text-3xl tracking-wider font-bold text-center">ĐHQG HỒ CHÍ MINH</div>
      </div>
      <img src={spso} alt="" className="place-self-center" />
    </div>
  ) : (
    <div className="w-full h-full grid grid-cols-2 grid-rows-1 gap-4 p-5">
      <div className="w-full flex items-center flex-col justify-center gap-5">
        <div className="w-full text-4xl tracking-wider font-bold text-center">DỊCH VỤ IN ẤN THÔNG MINH CHO SINH VIÊN TRƯỜNG ĐẠI HỌC BÁCH KHOA</div>
        <div className="w-full text-4xl tracking-wider font-bold text-center">ĐHQG HỒ CHÍ MINH</div>
        <div className="w-full text-xl tracking-wider font-bold text-center italic">In ấn thông minh – Tối ưu hóa thời gian, nâng tầm chất lượng.</div>
        <div to="login" className="text-2xl font-bold underline text-[#030391] cursor-pointer" onClick={() => navigate("/login", { replace: true })}>
          Đăng nhập để bắt đầu
        </div>
      </div>
      <img src={main} alt="" className="place-self-center" />
    </div>
  );
};

export default Main;
