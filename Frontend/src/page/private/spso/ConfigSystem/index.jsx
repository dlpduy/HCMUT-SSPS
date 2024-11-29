const ConfigSystem = () => {
  return (
    <div className="grid grid-cols-2 grid-rows-1 gap-4">
      <div className="flex flex-col">
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Thêm loại file được tải lên</div>
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Các loại file hiện tại</div>
      </div>
      <div className="flex flex-col">
        <div className="w-1/2 border-b border-slate-400 pb-3 text-2xl font-bold text-darkblue">Thông tin giấy</div>
        <div className="pb-3 text-2xl font-bold text-darkblue">Các loại giấy hiện tại</div>
      </div>
    </div>
  );
};

export default ConfigSystem;
