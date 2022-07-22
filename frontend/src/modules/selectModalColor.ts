import { computed } from "vue";

export default function selectModalColor(data: string): object {
  const modalStyle = computed((): string => {
    return `${data}-modal`;
  });

  return {
    modalStyle,
  };
}
