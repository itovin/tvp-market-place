interface Props {
  htmlFor: string;
  label: string;
  value?: string;
  name: string;
  id: string;
  type: string;
  placeHolder: string;
  min?: number;
  max?: number;
  pattern?: string;
  isRequired: boolean;
  error?: string;
  handleOnChange?: (input: string) => void;
}

export default function Textbox({
  htmlFor,
  label,
  value,
  name,
  id,
  type,
  placeHolder,
  min,
  max = 255,
  pattern,
  handleOnChange,
  isRequired,
  error,
}: Props) {
  return (
    <div>
      <div className="flex items-center justify-between">
        <label
          htmlFor={htmlFor}
          className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
        >
          {label}
        </label>
        {error && <p className="text-red-500 block mb-2 text-sm font-medium text-gray-900 dark:text-white">{error}</p>}
      </div>
      <input
        value={value}
        type={type}
        name={name}
        id={id}
        minLength={min}
        maxLength={max}
        pattern={pattern}
        placeholder={placeHolder}
        onChange={(e) => handleOnChange?.(e.target.value)}
        required={isRequired}
        autoComplete="off"
        className="
          bg-gray-50 
          border 
          border-gray-300 
          text-gray-900 text-sm 
          rounded-lg f
          ocus:ring-primary-600 
          focus:border-primary-600 
          block w-full p-2.5 
          dark:bg-gray-700 
          dark:border-gray-600 
          dark:placeholder-gray-400 
          dark:text-white dark:focus:ring-blue-500 
          dark:focus:border-blue-500"
      />
    </div>
  );
}
