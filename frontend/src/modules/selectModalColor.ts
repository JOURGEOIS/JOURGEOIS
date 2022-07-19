import { computed } from "vue";

export default function selectModalColor(data: string): object {
  const modalStyle = computed((): string => {
    if (data === "purple") {
      return "purple-modal";
    } else {
      return "white-modal";
    }
  });

  return {
    modalStyle,
  };
}
