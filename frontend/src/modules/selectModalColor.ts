import { computed } from "vue";

export default function selectModalColor(data: string): object {
  const modalStyle = computed((): string => {
    if (data === "primary") {
      return "primary-modal";
    } else {
      return "white-modal";
    }
  });

  return {
    modalStyle,
  };
}
